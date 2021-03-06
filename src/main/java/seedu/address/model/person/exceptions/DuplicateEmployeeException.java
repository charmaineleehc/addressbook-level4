package seedu.address.model.person.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Employee objects.
 */
public class DuplicateEmployeeException extends DuplicateDataException {
    public DuplicateEmployeeException() {
        super("Operation would result in duplicate persons");
    }
}
