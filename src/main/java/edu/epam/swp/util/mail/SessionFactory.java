package edu.epam.swp.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;


/**
 * SessionFactory is used for MailSender.
 * @author romab
 */
public class SessionFactory {

    private SessionFactory() {}

    /**
     * Create session session.
     * @param properties Properties object.
     * @return Session object.
     */
    public static Session createSession(Properties properties) {
        String userName = properties.getProperty("mail.user.name");
        String userPassword = properties.getProperty("mail.user.password");
        return Session.getDefaultInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName,userPassword);
            }
        });
    }
}
