package com.hanyang.startup.hanyangstartup.common.service;


import com.hanyang.startup.hanyangstartup.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void sendWelComeEmail(String to,User user) throws MessagingException, UnsupportedEncodingException {
        Context context = new Context();
        context.setVariable("user", user);

        System.out.println("메일 발송@@@@");
        String process = templateEngine.process("email/welcome", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(new InternetAddress("a901119@gmail.com","한양대학교 창업지원단"));
        helper.setTo(to);
        helper.setSubject("한양대학교 창업지원단 가입 안내 메일입니다. ");
        helper.setText(process, true);

        javaMailSender.send(mimeMessage);
//        return "Sent";
    }
}
