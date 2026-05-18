package com.aston.dao;

import com.aston.model.User;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // один объект для всех тестов
class UserDaoIntegrationTest {

    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15");

    private UserDao userDao;

    @BeforeAll // выполняется один раз перед всеми тестами
    void setup() {
        postgres.start();

        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());

        userDao = new UserDaoImpl();
    }

    @AfterAll // после всех тестов stop
    void stop() {
        postgres.stop();
    }

    @BeforeEach // перед каждым тестом удаляем пользователей, делаем тест изолированным
    void clean() {
        userDao.getAll().forEach(u -> userDao.delete(u.getId()));
    }

    @DisplayName("Сохранение пользователя должно генерировать ID")
    @Test
    void save_shouldPersistUser() {
        User user = new User("Test", "test@mail.com", 20);

        userDao.save(user);

        assertNotNull(user.getId());
    }

    @DisplayName("Получение пользователя по ID должно возвращать корректного пользователя")
    @Test
    void getById_shouldReturnUser() {
        User user = new User("Alice", "a@mail.com", 25);
        userDao.save(user);

        User found = userDao.getById(user.getId());

        assertNotNull(found);
        assertEquals("Alice", found.getName());
    }

    @DisplayName("Получение всех пользователей должно возвращать список пользователей")
    @Test
    void getAll_shouldReturnUsers() {
        userDao.save(new User("A", "a@mail.com", 20));
        userDao.save(new User("B", "b@mail.com", 30));

        assertTrue(userDao.getAll().size() >= 2);
    }

    @DisplayName("Удаление пользователя должно удалять запись из базы")
    @Test
    void delete_shouldRemoveUser() {
        User user = new User("Delete", "d@mail.com", 40);
        userDao.save(user);

        userDao.delete(user.getId());

        assertNull(userDao.getById(user.getId()));
    }

    @DisplayName("Обновление пользователя должно изменять его данные")
    @Test
    void update_shouldChangeUser() {
        User user = new User("Old", "old@mail.com", 20);
        userDao.save(user);

        user.setName("New");
        userDao.update(user);

        User updated = userDao.getById(user.getId());

        assertEquals("New", updated.getName());
    }
}