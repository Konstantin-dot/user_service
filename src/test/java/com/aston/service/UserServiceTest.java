package com.aston.service;

import com.aston.dao.UserDao;
import com.aston.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Подключение Mockito
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @DisplayName("Создание пользователя — успешный вызов DAO")
    @Test
    void createUser_shouldCallDao() {
        User user = new User("John", "john@mail.com", 25);

        userService.createUser(user);

        verify(userDao).save(user);
    }

    @DisplayName("Создание пользователя с отрицательным возрастом — выбрасывает исключение")
    @Test
    void createUser_negativeAge_shouldThrowException() {
        User user = new User("John", "john@mail.com", -5);

        assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(user));
    }

    @DisplayName("Получение пользователя по ID — возвращает пользователя")
    @Test
    void getUser_shouldReturnUser() {
        User user = new User("John", "mail", 20);
        when(userDao.getById(1L)).thenReturn(user);

        User result = userService.getUser(1L);

        assertEquals("John", result.getName());
    }

    @DisplayName("Получение всех пользователей — возвращает список")
    @Test
    void getAllUsers_shouldReturnList() {
        when(userDao.getAll()).thenReturn(List.of(new User()));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
    }

    @DisplayName("Удаление пользователя — вызывает DAO.delete()")
    @Test
    void deleteUser_shouldCallDao() {
        userService.deleteUser(1L);

        verify(userDao).delete(1L);
    }

    @DisplayName("Создание пользователя с ошибкой — DAO.save() не вызывается")
    @Test
    void createUser_negativeAge_shouldNotCallDao() {
        User user = new User("John", "john@mail.com", -5);

        assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(user));

        verify(userDao, never()).save(any());
    }
}