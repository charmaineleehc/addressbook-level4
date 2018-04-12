package seedu.carvicim.logic.commands;

import java.io.IOException;

import seedu.carvicim.commons.GmailAuthenticator;

//@@author charmaineleehc
//This command was needed intially as I wanted to allow the user to login to their own account. However, I later
//decided that it would be better if CarviciM had its own Gmail account and the emails are sent from that account.
/**
 * Directs user to the login page of Gmail for user to log in.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in user into Gmail account.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "You have successfully logged into your Gmail account!";

    private boolean isLoggedIn;

    private final GmailAuthenticator gmailAuthenticator = new GmailAuthenticator();

    @Override
    public CommandResult execute() {
        String authenticationUrl = gmailAuthenticator.getAuthenticationUrl();
        try {
            new GmailAuthenticator();
        } catch (IOException ioe) {
            System.exit(1);
        }

        isLoggedIn = true;
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
