package com.khmervote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("កូដផ្ទៀងផ្ទាត់សម្រាប់ការបោះឆ្នោតឌីជីថល");
        message.setText("សួស្តី! កូដផ្ទៀងផ្ទាត់របស់អ្នកគឺ៖ " + code + "\nកូដនេះនឹងផុតកំណត់ក្នុងរយៈពេល ១៥ នាទី។");

        mailSender.send(message);
    }
}
