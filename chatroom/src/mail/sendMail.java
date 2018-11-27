package mail;


import java.util.Properties;

import javax.mail.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class sendMail {
    private static final String USERNAME = "gaoooyh@163.com";//用户名
    private static final String PASSWORD = "gaoooyh511";//密码
    public sendMail() {
    // Recipient's email ID needs to be mentioned.
        String to = "837948421@qq.com";//change accordingly

        // Sender's email ID needs to be mentioned
        String from = "yhgao8379@gmail.com";//change accordingly
        final String username = "yhgao8379";//change accordingly
        final String password = "837948421a";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Testing Subject");

            // Now set the actual message
            message.setText("Hello, this is sample for to check send "
                    + "email using JavaMailAPI ");

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
