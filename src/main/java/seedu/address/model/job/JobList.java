package seedu.address.model.job;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.job.exceptions.JobNotFoundException;

/**
 * A list of jobs that does not allow nulls
 */
public class JobList implements Iterable<Job> {

    private final ObservableList<Job> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent employee as the given argument
     */
    public boolean contains(Job toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a job to the list
     *
     */
    public void add(Job toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent job from the list
     *
     * @throws JobNotFoundException if no such job could be found in the list
     */
    public boolean remove(Job toRemove) throws JobNotFoundException {
        requireNonNull(toRemove);
        final boolean jobFoundAndDeleted = internalList.remove(toRemove);
        return jobFoundAndDeleted;
    }

    @Override
    public Iterator<Job> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobList // instanceof handles nulls
                && this.internalList.equals(((JobList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
