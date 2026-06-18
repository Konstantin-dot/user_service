package com.aston.kafka;

import com.aston.constants.EventTypes;
import com.aston.constants.NotificationConstants;
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
            topics = "${app.kafka.topic}",
            groupId = "${app.kafka.group-id}"
    )
    public void listen(UserEvent event) {

        if (EventTypes.CREATE.equals(event.getOperation())) {

            emailService.send(
                    event.getEmail(),
                    NotificationConstants.ACCOUNT_CREATED_SUBJECT,
                    NotificationConstants.ACCOUNT_CREATED_TEXT
            );
        }

        if (EventTypes.DELETE.equals(event.getOperation())) {

            emailService.send(
                    event.getEmail(),
                    NotificationConstants.ACCOUNT_DELETED_SUBJECT,
                    NotificationConstants.ACCOUNT_DELETED_TEXT
            );
        }
    }
}