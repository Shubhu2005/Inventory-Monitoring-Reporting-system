package com.inventory.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.*;

public class EmailUtil {

    private static final Map<String, String> otpStorage = new HashMap<>();

    public static String sendOtpEmail(String toEmail) {
        final String fromEmail = System.getenv("MAIL_USER");
        final String password = System.getenv("MAIL_PASS");

        if (fromEmail == null || password == null) {
            System.out.println("❌ Environment variables MAIL_USER or MAIL_PASS not set!");
            return null;
        }

        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(toEmail, otp);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Inventory System - Email Verification OTP");
            message.setText("Your verification OTP is: " + otp + "\n\nValid for 5 minutes.");

            Transport.send(message);
            System.out.println("✅ OTP sent successfully to " + toEmail);
            return otp;

        } catch (MessagingException e) {
            System.out.println("❌ Failed to send email: " + e.getMessage());

            return null;
        }
    }

    public static boolean validateOTP(String email, String enteredOTP) {
        String storedOTP = otpStorage.get(email);
        if (storedOTP != null && storedOTP.equals(enteredOTP)) {
            otpStorage.remove(email);
            return true;
        }
        return false;
    }

    public static void sendReport(String toEmail, String subject, String body, String attachmentPath) {
        final String fromEmail = System.getenv("MAIL_USER");
        final String password = System.getenv("MAIL_PASS");

        if (fromEmail == null || password == null) {
            System.out.println("❌ Environment variables MAIL_USER or MAIL_PASS not set!");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachmentPath);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("✅ Report sent successfully to " + toEmail);

        } catch (Exception e) {
            System.out.println("❌ Failed to send report email: " + e.getMessage());

        }
    }
}
