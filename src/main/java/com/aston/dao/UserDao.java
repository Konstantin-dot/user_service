package com.aston.dao;

import com.aston.model.User;

import java.util.List;

// интерфейс DAO
public interface UserDao {
    void save(User user);

    User getById(Long id);

    List<User> getAll();

    void update(User user);

    void delete(Long id);
}