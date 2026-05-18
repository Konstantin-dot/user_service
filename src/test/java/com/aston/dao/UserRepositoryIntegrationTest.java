package com.aston.dao;

import com.aston.entity.User;
import com.aston.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class UserRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void setUp() {
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
    }

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @DisplayName("Сохранение пользователя в БД, генерация по id")
    @Test
    void save_shouldPersistUser() {
        User user = new User();
        user.setName("Test");
        user.setEmail("test@mail.com");
        user.setAge(20);

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
    }

    @DisplayName("Поиск по id в БД")
    @Test
    void findById_shouldReturnUser() {
        User user = userRepository.save(new User("A", "a@mail.com", 20));

        User found = userRepository.findById(user.getId()).orElse(null);

        assertNotNull(found);
        assertEquals("A", found.getName());
    }
}