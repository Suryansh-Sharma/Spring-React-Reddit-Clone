package com.suryansh.mail;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    @Autowired
    private final TemplateEngine templateEngine;

    public String buildAuthEmail(String message,String username) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("username", username);
        return templateEngine.process("mailTemplate", context);
    }

    public String buildCommentAlertEmail(String username,String RepliedBy,String postUrl){
        Context context = new Context();

        return templateEngine.process("commentAlertTemplate",context);
    }
}