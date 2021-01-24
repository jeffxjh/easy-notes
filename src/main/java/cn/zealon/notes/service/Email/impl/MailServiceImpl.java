package cn.zealon.notes.service.Email.impl;

import cn.zealon.notes.common.config.EmailConfig;
import cn.zealon.notes.service.Email.MailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    @Resource
    private JavaMailSender mailSender;

    @Resource
    private EmailConfig emailConfig;


    @Override
    public void sendMail(String to, String subject,String text,boolean isHtml) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true,"UTF-8");
        helper.setFrom(emailConfig.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text,isHtml);
        mailSender.send(mailMessage);
    }

}
