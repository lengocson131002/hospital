package com.hospital.booking.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Properties;

public class EmailUtils {

    public static boolean sendEmail(String fromAddress, String toAddress, String subject, String message) {
        try {
            // sets SMTP server properties
            Properties properties = new Properties();
            properties.put("mail.smtp.host", ApplicationSettings.getGmailHost());
            properties.put("mail.smtp.port", ApplicationSettings.getGmailPort());
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            // creates a new session with an authenticator
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(ApplicationSettings.getGmailUsername(), ApplicationSettings.getGmailPassword());
                }
            };

            Session session = Session.getInstance(properties, auth);

            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(fromAddress));
            InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent(message, "text/plain; charset=UTF-8");

            // sends the e-mail
            Transport.send(msg);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}