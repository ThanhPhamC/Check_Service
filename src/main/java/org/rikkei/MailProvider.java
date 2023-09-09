package org.rikkei;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

import static org.rikkei.Constant.*;

public class MailProvider {
    protected static void sendEmail(String subject, List<Service> services, Properties props) {
        try {
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(props.getProperty(MAIL_USERNAME), props.getProperty(MAIL_PASSWORD));
                }
            });
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, getListMail(props));
            message.setSubject(subject);
            message.setText(setContent(services));
            message.setSentDate(new Date());
            // send message
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static InternetAddress[] getListMail(Properties props) {
        String recipientEmails = props.getProperty(MAIL_RECEIVE);
        return Arrays.stream(recipientEmails.split(","))
                .map(email -> {
                    try {
                        return new InternetAddress(email);
                    } catch (AddressException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(InternetAddress[]::new);
    }
    private static String setContent(List<Service> services) {
        String content = MAIL_CONTENT;
        String serviceStatus = "";
        for (Service service : services) {
            serviceStatus += service.getServiceName() + ": IS NOT RUNNING\n                 ";
        }
        return content + serviceStatus;

    }

}
