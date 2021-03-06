package com.example.mail.service.impl;

import com.example.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * 项目: mail
 * 时间: 2021/11/14 20:59
 *
 * @author 黄宇
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final MailProperties mailProperties;

    private final MimeMessage mimeMessage;

    @Override
    public void sendText(String email, String subject, String text) {
        MimeMessageHelper helper = new MimeMessageHelper(this.mimeMessage);
        try {
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(this.mimeMessage);
    }

    @Override
    public void sendHtml(String email, String subject, String html, Map<String, File> files) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(this.mimeMessage, true);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(String.format(html, files.keySet().toArray()), true);
            for (String filename : files.keySet()) {
                helper.addInline(filename, files.get(filename));
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(this.mimeMessage);
    }

    @Override
    public void sendFile(String email, String subject, String text, Map<String, File> files) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(this.mimeMessage, true);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text);
            for (String filename : files.keySet()) {
                helper.addAttachment(filename, files.get(filename));
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(this.mimeMessage);
    }
}
