package com.expressbank.paydaytray.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMailMessage;

import javax.mail.internet.MimeMessage;

public interface MailService {
    void sendMail(SimpleMailMessage mailMessage);
    void sendMail(MimeMessage mimeMessage);
}
