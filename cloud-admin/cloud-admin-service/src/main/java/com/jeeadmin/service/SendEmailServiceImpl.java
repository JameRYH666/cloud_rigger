package com.jeeadmin.service;

import com.jeeadmin.api.ISendEmailService;
import com.jeerigger.frame.util.KeysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SendEmailServiceImpl implements ISendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private HttpSession httpSession;

    @Override
    public void sendEmailCode(String to, String subject) {
        //取出发送邮箱服务器的名字
        String from = mailProperties.getUsername();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        //生成随机的验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        //生成session的key
        String codeKey = String.format(KeysUtil.MAIL_CODE_KEY, to);
        httpSession.setAttribute(codeKey,code);
        mailMessage.setText(code);
        javaMailSender.send(mailMessage);
    }
}
