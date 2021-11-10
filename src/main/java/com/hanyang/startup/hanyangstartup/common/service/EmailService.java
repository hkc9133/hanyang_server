package com.hanyang.startup.hanyangstartup.common.service;


import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.common.domain.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value(value = "${config.adminEmail}")
    private String ADMIN_EMAIL;

    @Async
    public void sendWelComeEmail(String to,User user) throws MessagingException, UnsupportedEncodingException {
//        Context context = new Context();
//        context.setVariable("user", user);
//
//        String process = templateEngine.process("email/welcome", context);
//        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
//        helper.setFrom(new InternetAddress("startup@hanyang.ac.kr","한양대학교 창업지원단"));
////        helper.setTo(to);
//        helper.setTo(new InternetAddress(to,user.getUserName()));
//        helper.setSubject("한양대학교 창업지원단 가입 안내 메일입니다. ");
//        helper.setText(process, true);
//
//        javaMailSender.send(helper.getMimeMessage());
////        return "Sent";


        Email email = new Email();
        email.setToName(user.getUserName());
        email.setTo(to);
        email.setTitle("한양대학교 창업지원단 가입 안내 메일입니다");
        email.setDesc(user.getUserName()+"님 가입을 환영합니다.");

        Context context = new Context();
        context.setVariable("email", email);

        String process = templateEngine.process("email/email", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(new InternetAddress(ADMIN_EMAIL,"한양대학교 창업지원단"));
        helper.setTo(new InternetAddress(email.getTo(),email.getToName()));
        helper.setSubject(email.getTitle());
        helper.setText(process, true);

        javaMailSender.send(helper.getMimeMessage());
    }

    @Async
    public void sendEmail(Email email) throws MessagingException, UnsupportedEncodingException {
        Context context = new Context();
        context.setVariable("email", email);

        String process = templateEngine.process("email/email", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(new InternetAddress(ADMIN_EMAIL,"한양대학교 창업지원단"));
        helper.setTo(new InternetAddress(email.getTo(),email.getToName()));
        helper.setSubject(email.getTitle());
        helper.setText(process, true);

        javaMailSender.send(helper.getMimeMessage());
    }
}
