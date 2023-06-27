package com.rubyzli.mail_automation.logic;


import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailResponder {
    public void sendAutoResponse(String recipient, String subject, String body) {
        String senderEmail = "rebecasimon027@yahoo.com";
        String senderPassword = "lsqsuetldsdbenuf";

        String host = "smtp.mail.yahoo.com";
        int port = 465;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        session.setDebug(true);


        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Mail sent successfully!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void checkMailbox() {
        String receiverEmail = "rebecasimon027@yahoo.com";
        String receiverPassword = "lsqsuetldsdbenuf";

        String host = "imap.mail.yahoo.com";
        int port = 993;

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", host);
        props.setProperty("mail.imaps.port", String.valueOf(port));
        props.setProperty("mail.imaps.timeout", "5000");
        props.setProperty("mail.imaps.ssl.enable", "true");

        try {
            Session session = Session.getInstance(props, null);

            Store store = session.getStore();
            store.connect(host, port, receiverEmail, receiverPassword);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int totalMessages = inbox.getMessageCount();
            int startIndex = Math.max(totalMessages - 10, 1); // Get the last 10 messages or less if there are fewer messages
            Message[] messages = inbox.getMessages(startIndex, totalMessages);
            for (Message message : messages) {
                System.out.println("Iterating through messages");
                Address[] fromAddresses = message.getFrom();
                if (fromAddresses.length > 0) {
                    String senderEmail = ((InternetAddress) fromAddresses[0]).getAddress();
                    System.out.println("Sender Email: " + senderEmail);
                    if (senderEmail.equals("jr.simon07@gmail.com")) {
                        System.out.println("In the if statement");
                        String recipient = "jr.simon07@gmail.com";
                        String subject = "Auto-Response";
                        String body = "Thank you for your email. This is an automated response.";
                        sendAutoResponse(recipient, subject, body);
                    }
                }
            }

            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
