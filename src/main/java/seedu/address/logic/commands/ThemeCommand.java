package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.SetThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author owzhenwei
/**
 * Changes the theme of the application
 */
public class ThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";

    private static final int NUMBER_OF_THEMES = 2;

    private static final String MESSAGE_THEME_CHANGE_SUCCESS = "Theme updated: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Applies selected theme\n"
            + "1. Teal theme\n"
            + "2. Dark theme\n"
            + "Parameters: INDEX (positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    private final Index selectedIndex;

    public ThemeCommand(Index selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (selectedIndex.getZeroBased() >= NUMBER_OF_THEMES) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME_INDEX);
        }
        EventsCenter.getInstance().post(new SetThemeRequestEvent(selectedIndex));
        return new CommandResult(String.format(MESSAGE_THEME_CHANGE_SUCCESS, selectedIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.selectedIndex.equals(((ThemeCommand) other).selectedIndex)); // state check

    }
}
