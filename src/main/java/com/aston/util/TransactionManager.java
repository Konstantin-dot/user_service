package com.aston.util;

import com.aston.exception.DataAccessException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.postgresql.util.PSQLException;

import java.util.function.Function;

public class TransactionManager {

    public static <T> T execute(Function<Session, T> action) {
        Transaction tx = null; // создание транзакции

        try (Session session = HibernateUtil.getSessionFactory().openSession()) { // открытие сессии
            tx = session.beginTransaction(); // начало транзакции

            T result = action.apply(session); // выполнение транзакции для функции

            tx.commit(); //коммит
            return result;

        } catch (Exception e) {
            if (tx != null) tx.rollback(); // откат при ошибке
            throw mapException(e);
        }
    }

    private static RuntimeException mapException(Exception e) {

        // ошибки Postgres
        if (e.getCause() instanceof PSQLException || e instanceof PSQLException) {
            PSQLException pgEx = (PSQLException) (e instanceof PSQLException ? e : e.getCause());

            String sqlState = pgEx.getSQLState();

            return new DataAccessException(
                    "PostgreSQL error: " + explainPostgresError(sqlState), e
            );
        }

        // ошибки Hibernate
        if (e instanceof HibernateException || e.getCause() instanceof HibernateException) {
            return new DataAccessException("Hibernate error occurred", e);
        }

        // остальное
        return new DataAccessException("Unknown database error", e);
    }

    private static String explainPostgresError(String sqlState) {

        if (sqlState == null) return "Unknown SQL error";

        return switch (sqlState) {
            case "28P01" -> "Invalid username or password";
            case "23505" -> "Duplicate key violation";
            case "23503" -> "Foreign key violation";
            case "08001" -> "Unable to connect to database";
            default -> "SQLState: " + sqlState;
        };
    }
}