package edu.epam.swp.util.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * MailSender class is used to send emails.
 * @author romab
 */
public class MailSender {

    private static final Logger logger = LogManager.getLogger(MailSender.class);
    private MimeMessage message;
    private String sendToEmail;
    private String mailSubject;
    private String mailText;
    private Properties properties;

    /**
     * Instantiates a new Mail sender.
     * @param sendToEmail String containing the to email.
     * @param mailSubject String containing the mail's subject.
     * @param mailText String containing the mail's text.
     * @param properties Properties object.
     */
    public MailSender(String sendToEmail,String mailSubject,String mailText, Properties properties) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
        this.properties = properties;
    }

    /**
     * Sends email.
     */
    public void send() {
        try {
            initMessage();
            Transport.send(message);
        } catch (AddressException e) {
            logger.error("Invalid address {}",sendToEmail,e);
        } catch (MessagingException e) {
            logger.error("Error generating or sending message",e);
        }
    }

    /**
     * Initiates message.
     * @throws MessagingException
     */
    private void initMessage() throws MessagingException {
        Session mailSession = SessionFactory.createSession(properties);
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        message.setSubject(mailSubject);
        message.setContent(mailText,"text/html");
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(sendToEmail));
    }
}
