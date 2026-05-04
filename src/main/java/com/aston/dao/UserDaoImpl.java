package com.aston.dao;

import com.aston.model.User;
import com.aston.util.TransactionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// Реализация методов из интерфейса
public class UserDaoImpl implements UserDao {

    // Логгер
    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public void save(User user) {
        TransactionManager.execute(session -> {
            session.persist(user);
            log.info("User saved successfully: {}", user);
            return null;
        });
    }

    @Override
    public User getById(Long id) {
        return TransactionManager.execute(session -> {
            log.info("Fetching user by id: {}", id);
            User user = session.get(User.class, id);
            if (user == null) {
                log.warn("User not found with id: {}", id);
            }
            return user;
        });
    }

    @Override
    public List<User> getAll() {
        return TransactionManager.execute(session -> {
            log.info("Fetching all users");
            List<User> users = session.createQuery("from User", User.class).list();
            log.info("Found {} users", users.size());
            return users;
        });
    }

    @Override
    public void update(User user) {
        TransactionManager.execute(session -> {
            session.merge(user);
            log.info("User updated: {}", user);
            return null;
        });
    }

    @Override
    public void delete(Long id) {
        TransactionManager.execute(session -> {
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                log.warn("Deleted user with id: {}", id);
            } else {
                log.warn("User not found for deletion: {}", id);
            }
            return null;
        });
    }
}