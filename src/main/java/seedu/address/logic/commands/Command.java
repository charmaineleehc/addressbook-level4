package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.CommandWordException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    /**
     * Stores a list of COMMANDS by their command word
     */

    public static final String[] COMMANDS = {
        AddEmployeeCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD,
        DeleteEmployeeCommand.COMMAND_WORD,
        EditEmployeeCommand.COMMAND_WORD,
        ExitCommand.COMMAND_WORD,
        FindEmployeeCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD,
        HistoryCommand.COMMAND_WORD,
        ListEmployeeCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD,
        SetCommand.COMMAND_WORD,
        UndoCommand.COMMAND_WORD,
        ThemeCommand.COMMAND_WORD
    };

    protected Model model;
    protected CommandHistory history;
    protected UndoRedoStack undoRedoStack;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * @param key used to reference Command Word
     * @return  Command word used
     * @throws CommandWordException command word is not valid
     */
    public String getCommandWord(String key) throws CommandWordException {
        requireNonNull(model);
        return model.getCommandWords().getCommandWord(key);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
    }
}
