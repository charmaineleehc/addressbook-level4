package seedu.address.model.session.exceptions;

//@@author yuhongherald
/**
 * Represents an error from attempting to read an excel file in {@link seedu.address.model.session.ImportSession}.
 */
public class FileFormatException extends Exception {
    public FileFormatException(String message) {
        super(message);
    }
}
