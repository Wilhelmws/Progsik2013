package amu;

import amu.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

    public static void send(String to, String subject, String content) {
        Mailer.send(to, subject, content, Config.EMAIL_FROM_ADDR, Config.EMAIL_FROM_NAME);
    }

    public static void send(String to, String subject, String content, String fromAddr, String fromName) {

        Properties properties = new Properties();
        // properties.put("mail.smtp.debug", "true");

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        properties.put("mail.smtp.host", Config.EMAIL_SMTP_HOST);
        properties.put("mail.smtp.port", Config.EMAIL_SMTP_PORT);
        properties.put("mail.smtp.user", Config.EMAIL_SMTP_USER);

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.EMAIL_SMTP_USER, Config.EMAIL_SMTP_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddr, fromName));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");

            // The restriction to ntnu.no-recipients is here try to avoid spamming (too many) innocents.
            // Do not remove it before you've secured all mail-sending code.
            if (to.endsWith("ntnu.no")) {
                Transport.send(message);
            }
        } catch (Exception exception) {
            Logger.getLogger(Mailer.class.getName()).log(Level.SEVERE, null, exception);
        } finally {
            Mailer.log(to, subject, content);
        }
    }

    private static void log(String to, String subject, String content) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "INSERT INTO mail (sentTime, `to`, `subject`, content) VALUES (NOW(), ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, to);
            statement.setString(2, subject);
            statement.setString(3, content);
            statement.executeUpdate();
        } catch (SQLException exception) {
            Logger.getLogger(Mailer.class.getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSet);
        }
    }
}
