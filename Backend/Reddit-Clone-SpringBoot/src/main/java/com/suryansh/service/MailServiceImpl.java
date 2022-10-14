package com.suryansh.service;

import com.suryansh.dto.NotificationEmail;
import com.suryansh.exception.SpringRedditException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService{
    @Autowired
    private  JavaMailSender mailSender;
    @Autowired
    private  MailContentBuilder mailContentBuilder;


    @Async
    public void sendMail(NotificationEmail notificationEmail) throws  SpringRedditException {
        MimeMessagePreparator messagePreparatory = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparatory);
            log.info("Activation Email Sent !!");
        } catch (MailException e){
            throw new SpringRedditException("Exception Occurred during Mail Sending to "
            + notificationEmail.getRecipient());
        }
    }
}
