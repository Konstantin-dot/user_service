package com.aston;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {

        System.out.println(
                "ДЗ 5 \n" +
                        "В pom.xml добавил зависимости \n" +
                        "В application.properties добавил настройки микросервиса \n" +
                        "Создал UserEvent DTO-события \n" +
                        "Создал KafkaConsumerConfig конфиг для чтения сообщений \n" +
                        "Создал EmailService для отправки писем по Email \n" +
                        "Создал NotificationConsumer обработчик сообщений \n" +
                        "Создал EmailRequest это REST API для отправки email \n" +
                        "Создал NotificationController это REST API для отправки email вручную \n" +
                        "Создал docker compose.yml для запуска Kafka и Zookeeper из Docker \n" +
                        "Создал тесты на методы CREATE и DELETE и REST API \n");

                        // запуск kafka - (docker compose up)
                        // запуск user-service
                        // запуск notification-service
                        // тестировал в Postman

        SpringApplication.run(NotificationServiceApplication.class, args);
    }

}
