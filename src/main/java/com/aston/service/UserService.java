package com.aston.service;

import com.aston.dao.UserDao;
import com.aston.model.User;

import java.util.List;

// Управление бизнес-логикой
public class UserService {

    // Зависимость от DAO
    private final UserDao userDao;

    // конструктор
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // Создание пользователя
    public void createUser(User user) {
        if (user.getAge() < 0) {
            throw new IllegalArgumentException("Age can't be < null");
        }
        userDao.save(user);
    }

    // получение пользователя
    public User getUser(Long id) {
        return userDao.getById(id);
    }

    // получение всех пользователей
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    // обновление пользователей
    public void updateUser(User user) {
        userDao.update(user);
    }

    // удаление пользователей
    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}