package com.aston.kafka;

import com.aston.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final EmailService emailService;

    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "user-topic",
            groupId = "notification-group"
    )
    public void listen(UserEvent event) {

        if ("CREATE".equals(event.getOperation())) {

            emailService.send(
                    event.getEmail(),
                    "Account created",
                    "Здравствуйте! Ваш аккаунт был успешно создан."
            );
        }

        if ("DELETE".equals(event.getOperation())) {

            emailService.send(
                    event.getEmail(),
                    "Account deleted",
                    "Здравствуйте! Ваш аккаунт был удалён."
            );
        }
    }
}