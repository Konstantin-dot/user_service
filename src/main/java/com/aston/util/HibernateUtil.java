package com.aston.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// Фабрика соединения с БД
public class HibernateUtil {

    // Создание объекта конфигурации Hibernate
    private static final SessionFactory sessionFactory =
            new Configuration().configure().buildSessionFactory();

    // Метод для получения фабрики
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}