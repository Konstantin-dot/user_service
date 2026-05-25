package com.aston.dao;

import com.aston.entity.User;
import com.aston.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class UserRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url",
                postgres::getJdbcUrl);

        registry.add("spring.datasource.username",
                postgres::getUsername);

        registry.add("spring.datasource.password",
                postgres::getPassword);

        registry.add("spring.datasource.driver-class-name",
                postgres::getDriverClassName);

        registry.add("spring.jpa.hibernate.ddl-auto",
                () -> "create-drop");
    }

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @DisplayName("Сохранение пользователя в БД")
    @Test
    void save_shouldPersistUser() {

        User user = new User();
        user.setName("Test");
        user.setEmail("test@mail.com");
        user.setAge(20);

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
    }

    @DisplayName("Поиск пользователя по id")
    @Test
    void findById_shouldReturnUser() {

        User user = new User("A", "a@mail.com", 20);

        User saved = userRepository.save(user);

        User found = userRepository.findById(saved.getId())
                .orElse(null);

        assertNotNull(found);
        assertEquals("A", found.getName());
    }
}