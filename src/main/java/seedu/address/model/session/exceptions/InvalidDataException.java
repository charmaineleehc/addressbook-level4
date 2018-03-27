package seedu.address.model.session.exceptions;

//@@author yuhongherald
/**
 * Represents an error when data supplied to {@link seedu.address.model.session.SessionData} is in wrong format.
 */
public class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}
