package com.suryansh.service;

import com.suryansh.dto.NotificationEmail;

public interface MailService {
    void sendMail(NotificationEmail notificationEmail);
}
