package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Employee#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Employee> {

    private final ObservableList<Employee> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent employee as the given argument.
     */
    public boolean contains(Employee toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a employee to the list.
     *
     * @throws DuplicatePersonException if the employee to add is a duplicate of an existing employee in the list.
     */
    public void add(Employee toAdd) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the employee {@code target} in the list with {@code editedEmployee}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing employee in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setPerson(Employee target, Employee editedEmployee)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedEmployee);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedEmployee) && internalList.contains(editedEmployee)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedEmployee);
    }

    /**
     * Removes the equivalent employee from the list.
     *
     * @throws PersonNotFoundException if no such employee could be found in the list.
     */
    public boolean remove(Employee toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<Employee> employees) throws DuplicatePersonException {
        requireAllNonNull(employees);
        final UniquePersonList replacement = new UniquePersonList();
        for (final Employee employee : employees) {
            replacement.add(employee);
        }
        setPersons(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Employee> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Employee> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
