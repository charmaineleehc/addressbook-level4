package seedu.carvicim.model;

import static java.util.Objects.requireNonNull;
import static seedu.carvicim.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.carvicim.commons.core.ComponentManager;
import seedu.carvicim.commons.core.EventsCenter;
import seedu.carvicim.commons.core.LogsCenter;
import seedu.carvicim.commons.events.model.ArchiveEvent;
import seedu.carvicim.commons.events.model.CarvicimChangedEvent;
import seedu.carvicim.commons.events.ui.DisplayAllJobsEvent;
import seedu.carvicim.commons.events.ui.JobDisplayPanelResetRequestEvent;
import seedu.carvicim.commons.events.ui.JobListSwitchEvent;
import seedu.carvicim.logic.commands.CommandWords;
import seedu.carvicim.model.job.DateRange;
import seedu.carvicim.model.job.Job;
import seedu.carvicim.model.job.JobList;
import seedu.carvicim.model.job.exceptions.JobNotFoundException;
import seedu.carvicim.model.person.Employee;
import seedu.carvicim.model.person.exceptions.DuplicateEmployeeException;
import seedu.carvicim.model.person.exceptions.EmployeeNotFoundException;
import seedu.carvicim.storage.session.ImportSession;
import seedu.carvicim.ui.JobListIndicator;

/**
 * Represents the in-memory model of the carvicim book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final String ONE_AS_STRING = "1";

    private final Carvicim carvicim;
    private final FilteredList<Employee> filteredEmployees;
    private final FilteredList<Job> filteredJobs;
    private final CommandWords commandWords;

    private boolean isViewingImportedJobs;

    /**
     * Initializes a ModelManager with the given carvicim and userPrefs.
     */
    public ModelManager(ReadOnlyCarvicim carvicim, UserPrefs userPrefs) {
        super();
        requireAllNonNull(carvicim, userPrefs);

        logger.fine("Initializing with carvicim book: " + carvicim + " and user prefs " + userPrefs);

        this.carvicim = new Carvicim(carvicim);
        filteredEmployees = new FilteredList<>(this.carvicim.getEmployeeList());
        filteredJobs = new FilteredList<>(this.carvicim.getJobList());
        this.commandWords = userPrefs.getCommandWords();
        isViewingImportedJobs = false;
    }

    public ModelManager() {
        this(new Carvicim(), new UserPrefs());
    }

    //@@author yuhongherald
    @Override
    public boolean isViewingImportedJobs() {
        return isViewingImportedJobs;
    }

    @Override
    public void switchJobView() {
        isViewingImportedJobs = !isViewingImportedJobs;
        if (isViewingImportedJobs) {
            EventsCenter.getInstance().post(
                    new JobListSwitchEvent(JobListIndicator.IMPORTED));
        } else {
            EventsCenter.getInstance().post(
                    new JobListSwitchEvent(JobListIndicator.SAVED));
        }
    }

    @Override
    public void resetJobView() {
        ObservableList<Job> jobList;
        if (isViewingImportedJobs) {
            jobList = FXCollections.observableList(
                    ImportSession.getInstance().getSessionData().getUnreviewedJobEntries());
        } else {
            updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
            jobList = getFilteredJobList();
        }
        EventsCenter.getInstance().post(
                new DisplayAllJobsEvent(FXCollections.unmodifiableObservableList(jobList)));
    }

    @Override
    public void showOngoingJobs() {
        updateFilteredJobList(PREDICATE_SHOW_ONGOING_JOBS);
        EventsCenter.getInstance().post(
                new DisplayAllJobsEvent(FXCollections.unmodifiableObservableList(getFilteredJobList())));
    }

    @Override
    public void resetJobDisplayPanel() {
        EventsCenter.getInstance().post(new JobDisplayPanelResetRequestEvent());
    }

    //@@author
    @Override
    public void resetData(ReadOnlyCarvicim newData, CommandWords newCommandWords) {
        carvicim.resetData(newData);
        commandWords.resetData(newCommandWords);
        indicateAddressBookChanged();
    }

    @Override
    public CommandWords getCommandWords() {
        return commandWords;
    }

    @Override
    public String appendCommandKeyToMessage(String message) {
        StringBuilder builder = new StringBuilder(message);
        builder.append("\n");
        builder.append(commandWords.toString());
        return builder.toString();
    }

    @Override
    public ReadOnlyCarvicim getCarvicim() {
        return carvicim;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new CarvicimChangedEvent(carvicim));
    }

    /** Raises an event to indicate the model has changed */
    private void indicateArchiveEvent() {
        raise(new ArchiveEvent(carvicim));
    }

    @Override
    public synchronized void addJob(Job job) {
        carvicim.addJob(job);
        updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void closeJob(Job target, Job updatedJob) throws JobNotFoundException {
        carvicim.updateJob(target, updatedJob);
        updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized int archiveJob(DateRange dateRange) {
        int archiveJobCount;
        archiveJobCount = carvicim.archiveJob(dateRange);
        if (archiveJobCount != 0) {
            carvicim.removeArchivedJob();
            indicateArchiveEvent();
        }
        return archiveJobCount;
    }

    @Override
    public synchronized void deletePerson(Employee target) throws EmployeeNotFoundException {
        carvicim.removeEmployee(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addRemark(Job target, Job updatedJob) {
        carvicim.updateJob(target, updatedJob);
        updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Employee employee) throws DuplicateEmployeeException {
        carvicim.addEmployee(employee);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addJobs(List<Job> jobs) {
        for (Job job : jobs) {
            addMissingEmployees(job.getAssignedEmployeesAsSet());
            addJob(job);
        }
    }

    @Override
    public void addMissingEmployees(Set<Employee> employees) {
        Iterator<Employee> employeeIterator = employees.iterator();
        while (employeeIterator.hasNext()) {
            try {
                addPerson(employeeIterator.next());
            } catch (DuplicateEmployeeException e) {
                // discard the result
            }
        }
    }

    @Override
    public void updatePerson(Employee target, Employee editedEmployee)
            throws DuplicateEmployeeException, EmployeeNotFoundException {
        requireAllNonNull(target, editedEmployee);

        carvicim.updateEmployee(target, editedEmployee);
        indicateAddressBookChanged();
    }

    @Override
    public void sortPersonList() {
        carvicim.sortList();
        indicateAddressBookChanged();
    }

    @Override
    public JobList analyseJob(JobList jobList) {
        return carvicim.analyseJob(jobList);
    }

    //=========== Filtered Employee List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Employee} backed by the internal list of
     * {@code carvicim}
     */
    @Override
    public ObservableList<Employee> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredEmployees);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Employee> predicate) {
        requireNonNull(predicate);
        filteredEmployees.setPredicate(predicate);
    }

    //=========== Filtered Job List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Job} backed by the internal list of
     * {@code carvicim}
     */
    @Override
    public ObservableList<Job> getFilteredJobList() {
        return FXCollections.unmodifiableObservableList(filteredJobs);
    }

    @Override
    public void updateFilteredJobList(Predicate<Job> predicate) {
        requireNonNull(predicate);
        filteredJobs.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return carvicim.equals(other.carvicim)
                && filteredEmployees.equals(other.filteredEmployees)
                && filteredJobs.equals(other.filteredJobs)
                && commandWords.equals(other.getCommandWords());
    }

}
