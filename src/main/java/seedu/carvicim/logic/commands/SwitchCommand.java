package seedu.carvicim.logic.commands;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.carvicim.commons.core.EventsCenter;
import seedu.carvicim.commons.events.ui.DisplayAllJobsEvent;
import seedu.carvicim.model.job.Job;
import seedu.carvicim.storage.session.ImportSession;

/**
 * Lists all persons in the carvicim book to the user.
 */
//@@author yuhongherald
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_SUCCESS = "Switched job lists.";


    @Override
    public CommandResult execute() {
        model.switchJobView();
        ObservableList<Job> jobList;
        if (model.isViewingImportedJobs()) {
            jobList = FXCollections.observableList(
                    ImportSession.getInstance().getSessionData().getUnreviewedJobEntries());
        } else {
            jobList = model.getFilteredJobList();
        }
        EventsCenter.getInstance().post(
                new DisplayAllJobsEvent(FXCollections.unmodifiableObservableList(jobList)));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
