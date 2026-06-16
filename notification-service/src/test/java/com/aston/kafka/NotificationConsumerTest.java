package com.aston.kafka;

import com.aston.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class NotificationConsumerTest {

    @Autowired
    private NotificationConsumer consumer;

    @MockBean
    private EmailService emailService;

    @DisplayName("Тест логики Create")
    @Test
    void shouldSendEmailOnCreateEvent() {

        UserEvent event = new UserEvent("CREATE", "test@mail.com");

        consumer.listen(event);

        verify(emailService).send(
                "test@mail.com",
                "Account created",
                "Здравствуйте! Ваш аккаунт был успешно создан."
        );
    }

    @DisplayName("Тест логики Delete")
    @Test
    void shouldSendEmailOnDeleteEvent() {

        UserEvent event = new UserEvent("DELETE", "test@mail.com");

        consumer.listen(event);

        verify(emailService).send(
                "test@mail.com",
                "Account deleted",
                "Здравствуйте! Ваш аккаунт был удалён."
        );
    }
}