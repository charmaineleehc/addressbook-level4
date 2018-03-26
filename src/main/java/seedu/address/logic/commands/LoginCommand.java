package seedu.address.logic.commands;

public class LoginCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in user into Gmail account.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Please enter your authentication information";

    @Override
    public CommandResult execute() {
        model.sortPersonList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
