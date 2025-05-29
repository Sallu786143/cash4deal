package com.example.utility;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Async
    @Retryable(
            value = {MessagingException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)  // wait 5 seconds between retries
    )
    public void sendRegistrationMail(String to, String name) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", name);

        String htmlContent = templateEngine.process("registration-success", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Registration Successful");
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
    @Retryable(
            value = MessagingException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void sendLoginCode(String to, String code) throws MessagingException {
        Context context = new Context();
        context.setVariable("code", code); // for Thymeleaf

        String html = templateEngine.process("login-template", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Your Login Code");
        helper.setText(html, true); // true = HTML

        mailSender.send(message);
    }
}