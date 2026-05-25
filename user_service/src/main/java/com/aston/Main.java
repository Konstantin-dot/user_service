package com.aston;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        System.out.println(
                "ДЗ №2 \n" +
                        "Используется Hibernate без Spring \n" +
                        "Подключение к БД PostgreSQL \n" +
                        "Hibernate используется без Spring с использованием hibernate.cfg.xml \n" +
                        "Реализованы CRUD-операции \n" +
                        "Используется консольный интерфейс \n" +
                        "Зависимости выполнены в Maven \n" +
                        "Выполнено логирование SLF4J, Logback, логирование DAO, логирование операций \n" +
                        "Выполнена транзакционность, вынесена в TransactionManager, rollback при ошибках, commit централизован \n" +
                        "DAO-паттерн реализован, (изоляция доступа к БД) \n" +
                        "Для оборачивания исключений Hibernate + PostgreSQL реализован DataAccessException \n" +
                        "ДЗ 3 \n" +
                        "Добавил класс бизнес-логики UserService \n" +
                        "Добавил зависимости JUnit 5, Mockito в Maven \n" +
                        "Создал класс UserServiceTest с Unit тестами \n" +
                        "Добавил зависимости по Testcontainers + PostgreSQL в Maven \n" +
                        "Создал класс UserDaoIntegrationTest c Integration тестами \n" +
                        "ДЗ 4 \n" +
                        "Добавил в pom.xml стартеры Boot, Web, JPA \n" +
                        "Добавил application.properties -> удалил hibernate.cfg.xml \n" +
                        "Удалил HibernateUtil, теперь Spring сам управляет EntityManagerFactory \n" +
                        "Удалил TransactionManager, теперь Spring сам управляет жизненным циклом \n" +
                        "Удалил UserDaoImpl - его заменяет SpringDataJpa \n" +
                        "Пакет model переименовал в entity \n" +
                        "Добавил UserRepository -> Интерфейс SpringDataJpa \n" +
                        "В UserService добавил Аннотацию @Service \n" +
                        "Добавил UserDto для передачи данных между слоями \n" +
                        "Добавил UserMapper для преобразования User в UserDto \n" +
                        "Добавил RestController для принятия HTTP-запросов и делегирования \n" +
                        "Удалил DataAccessException - с Hibernate напрямую больше не работаем \n" +
                        "Добавил GlobalException - обработчик \n" +
                        "Добавил ControllerTest \n" +
                        "Изменил интеграционные тесты Dao на Repository \n" +
                        "Исправил UserServiceTest \n" +
                        "Тестировал приложение в Postman \n" +
                        "ДЗ 5 \n" +
                        "Добавил в pom.xml зависимость kafka \n" +
                        "Создал UserEvent DTO-события \n" +
                        "Создал KafkaProducerConf конфигурация отправителя сообщений в kafka \n" +
                        "Создал KafkaProducerService выполняет отправку сообщений в kafka \n" +
                        "В UserService добавил зависимость kafka и поправил методы CREATE и DELETE \n" +
                        "В application.properties добавил порт для подключения к Kafka-брокеру \n" +
                        "Поправил существующие тесты \n");

        SpringApplication.run(Main.class, args);
    }
}



