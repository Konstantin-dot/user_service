import com.aston.dao.UserDao;
import com.aston.dao.UserDaoImpl;
import com.aston.model.User;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final UserDao userDao = new UserDaoImpl();

    public static void main(String[] args) {

        System.out.println(
                "Используется Hibernate без Spring \n" +
                "Подключение к БД PostgreSQL \n" +
                "Hibernate используется без Spring с использованием hibernate.cfg.xml \n" +
                "Реализованы CRUD-операции \n" +
                "Используется консольный интерфейс \n" +
                "Зависимости выполнены в Maven \n" +
                "Выполнено логирование SLF4J, Logback, логирование DAO, логирование операций \n" +
                "Выполнена транзакционность, вынесена в TransactionManager, rollback при ошибках, commit централизован \n" +
                "DAO-паттерн реализован, (изоляция доступа к БД) \n" +
                "Для оборачивания исключений Hibernate + PostgreSQL реализован DataAccessException \n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                        1. Create user
                        2. Get all users
                        3. Get user by ID
                        4. Update user
                        5. Delete user
                        0. Exit
                    """);

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = scanner.next();
                    System.out.print("Email: ");
                    String email = scanner.next();
                    System.out.print("Age: ");
                    int age = scanner.nextInt();

                    userDao.save(new User(name, email, age));
                }

                case 2 -> {
                    List<User> users = userDao.getAll();
                    users.forEach(u -> System.out.println(u.getId() + " " + u.getName()));
                }

                case 3 -> {
                    System.out.print("ID: ");
                    Long id = scanner.nextLong();
                    System.out.println(userDao.getById(id));
                }

                case 4 -> {
                    System.out.print("ID: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    User user = userDao.getById(id);
                    if (user != null) {
                        System.out.print("New name: ");
                        user.setName(scanner.nextLine());
                        System.out.print("New email: ");
                        user.setEmail(scanner.nextLine());
                        System.out.print("New age: ");
                        user.setAge(scanner.nextInt());
                        userDao.update(user);
                    } else {
                        System.out.println("User not found");
                    }
                }

                case 5 -> {
                    System.out.print("ID: ");
                    Long id = scanner.nextLong();
                    userDao.delete(id);
                }

                case 0 -> System.exit(0);
            }
        }
    }
}