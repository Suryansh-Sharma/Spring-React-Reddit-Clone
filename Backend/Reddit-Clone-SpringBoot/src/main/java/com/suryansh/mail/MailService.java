package com.suryansh.mail;

import com.suryansh.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendAuthTokenMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparatory = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            String content = mailContentBuilder
                    .buildAuthEmail(notificationEmail.getBody(),
                            notificationEmail.getUserName());
            messageHelper.setText(content,true);
        };
        try {
            mailSender.send(messagePreparatory);
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new MailSendException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }
}
