package com.example.Tutorial.email;

import jakarta.mail.internet.MimeMessage;

public interface EmailSender {
    void send(String to, String email);

}
