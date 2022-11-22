package com.suryansh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
    private String userName;
    private String sender;
    private String subject;
    private String recipient;
    private String body;
}
