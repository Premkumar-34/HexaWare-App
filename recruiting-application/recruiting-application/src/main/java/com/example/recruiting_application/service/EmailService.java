package com.example.recruiting_application.service;

import com.example.recruiting_application.dto.MailDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
//to send the mail
public class EmailService {
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    //to send the mail to user
    public void sendSimpleMessage(MailDto mailBody) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(mailBody.getTo());
            mailMessage.setFrom(mailBody.getFrom());
            mailMessage.setSubject(mailBody.getSubject());
            mailMessage.setText(mailBody.getText());

            javaMailSender.send(mailMessage);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
