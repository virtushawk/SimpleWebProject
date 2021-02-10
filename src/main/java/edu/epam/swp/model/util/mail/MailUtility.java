package edu.epam.swp.model.util.mail;

import edu.epam.swp.model.exception.PropertyReaderException;
import edu.epam.swp.model.reader.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class MailUtility {
    private static final Logger logger = LogManager.getLogger(MailUtility.class);
    private static final String MESSAGE_TEXT = "confirmation link : http://localhost:8080/SWP_war_exploded/controller?command=confirm_email&id=%d";
    private static final String MESSAGE_SUBJECT = "Email confirmation";
    private static final String MAIL_PROPERTIES = "property/mail.properties";

    private MailUtility() {

    }

    public static void sendConfirmMessage(String email,long id) {
        PropertyReader propertyReader = new PropertyReader();
        try {
            Properties properties = propertyReader.readProperty(MAIL_PROPERTIES);
            String messageText = String.format(MESSAGE_TEXT,id);
            MailSender sender = new MailSender(email,MESSAGE_SUBJECT,messageText,properties);
            sender.send();
        } catch (PropertyReaderException e) {
            logger.error("Error while reading email properties",e);
        }
    }

}
