package edu.epam.swp.util.mail;

import edu.epam.swp.exception.PropertyReaderException;
import edu.epam.swp.model.reader.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * MailUtility is used to send emails.
 * @author romab
 */
public class MailUtility {

    private static final Logger logger = LogManager.getLogger(MailUtility.class);
    private static final String MESSAGE_CONFIRM_TEXT = "confirmation link : http://localhost:8080/SWP_war_exploded/controller?command=confirm_email&key=%s";
    private static final String MESSAGE_RESTORE_TEXT = "Your new password : %s";
    private static final String MESSAGE_SUBJECT_CONFIRMATION = "Email confirmation";
    private static final String MESSAGE_SUBJECT_RESTORE_PASSWORD = "restore Password";
    private static final String MAIL_PROPERTIES = "property/mail.properties";

    private MailUtility() {

    }

    /**
     * Sends confirm email message.
     * @param email String containing the email.
     * @param confirmationKey String containing the confirmation key.
     */
    public static void sendConfirmMessage(String email,String confirmationKey) {
        PropertyReader propertyReader = new PropertyReader();
        try {
            Properties properties = propertyReader.readProperty(MAIL_PROPERTIES);
            String messageText = String.format(MESSAGE_CONFIRM_TEXT,confirmationKey);
            MailSender sender = new MailSender(email,MESSAGE_SUBJECT_CONFIRMATION,messageText,properties);
            sender.send();
        } catch (PropertyReaderException e) {
            logger.error("Error while reading email properties",e);
        }
    }

    /**
     * Sends restore password message.
     * @param email String containing the email.
     * @param password String containing the password.
     */
    public static void sendRestoreMessage(String email,String password) {
        PropertyReader propertyReader = new PropertyReader();
        try {
            Properties properties = propertyReader.readProperty(MAIL_PROPERTIES);
            String messageText = String.format(MESSAGE_RESTORE_TEXT,password);
            MailSender sender = new MailSender(email,MESSAGE_SUBJECT_RESTORE_PASSWORD,messageText,properties);
            sender.send();
        } catch (PropertyReaderException e) {
            logger.error("Error while reading email properties",e);
        }
    }

}
