package seedu.address.logic.commands;

import seedu.address.model.AddressBook;

/**
 * Sorts all persons alphabetically by names in the address book to the user.
 */
public class SortEmployeeCommand extends Command {

    public static final String COMMAND_WORD = "sorte";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all persons in the address book as a list in alphabetical order.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    protected AddressBook addressBook;

    @Override
    public CommandResult execute() {
        model.sortPersonList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
