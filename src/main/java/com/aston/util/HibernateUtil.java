package com.aston.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// Фабрика соединения с БД
public class HibernateUtil {

    // Создание объекта конфигурации Hibernate
    private static SessionFactory sessionFactory;

    // Метод для получения фабрики
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) { // Добавил проверку, что DAO не вызывается при ошибке
            sessionFactory = new Configuration()
                    .configure()
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

}