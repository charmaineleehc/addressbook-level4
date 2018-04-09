package seedu.carvicim.logic.commands;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.collections.ObservableList;
import org.apache.commons.codec.binary.Base64;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import seedu.carvicim.commons.GmailAuthenticator;
import seedu.carvicim.commons.core.Messages;
import seedu.carvicim.logic.commands.exceptions.CommandException;
import seedu.carvicim.model.job.Job;
import seedu.carvicim.model.job.JobNumber;

import static java.util.Objects.requireNonNull;
import static seedu.carvicim.logic.parser.CliSyntax.PREFIX_JOB_NUMBER;

//@@author charmaineleehc
/**
 * Sends email to employee.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": emails employee about job details.\n"
            + "Parameters: "
            + PREFIX_JOB_NUMBER + "JOB_NUMBER "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_JOB_NUMBER + "12 ";

    public static final String MESSAGE_SUCCESS = "The email has been successfully sent to your employee!";

    //these fields are temporarily static final, will no longer be so in v1.5
    private static final String CARVICIM_EMAIL = "me";
    private static final String EMAIL_ADDRESS = "carvicim@gmail.com";
    private static final String EMAIL_SUBJECT = "Job details";
    private static final String EMAIL_CONTENT = "Hello! :)";

    private final JobNumber jobNumber;

    /**
     * @param jobNumber of the job details to be sent via email to the employee(s) in charge
     */
    public EmailCommand(JobNumber jobNumber) {
        this.jobNumber = jobNumber;
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    /*public static MimeMessage createEmail(
            String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }*/

    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    /*public static Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }*/

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param emailContent Email to be sent.
     * @return The sent message
     * @throws MessagingException
     * @throws IOException
     */
    /*public static Message sendMessage(
            Gmail service, String userId, MimeMessage emailContent) throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        return message;
    }*/

    @Override
    public CommandResult execute() throws CommandException {
        /*try {
            GmailAuthenticator gmailAuthenticator = new GmailAuthenticator();
            MimeMessage mimeMessage = createEmail(EMAIL_ADDRESS, EMAIL_ID, EMAIL_SUBJECT, EMAIL_CONTENT);
            sendMessage(gmailAuthenticator.getGmailService(), EMAIL_ID, mimeMessage);
        } catch (IOException | MessagingException e) {
            System.exit(1);
        }

        return new CommandResult(MESSAGE_SUCCESS);*/

        requireNonNull(model);
        ObservableList<Job> filteredJobList = model.getFilteredJobList();

        if (jobNumber.asInteger() >= filteredJobList.size() || jobNumber.asInteger() <= 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_NUMBER);
        }

        List<String> employeeEmails = filteredJobList.get(jobNumber.asInteger()).

    }

}
