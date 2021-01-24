package cn.zealon.notes.service.Email;

import javax.mail.MessagingException;

public interface MailService {
    /**
     * 发送邮件
     * @param to 邮件收件人
     * @param subject 邮件主题
     */
    public void sendMail(String to, String subject,String text,boolean isHtml) throws MessagingException;
}
