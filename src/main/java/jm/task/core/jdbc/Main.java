package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;


import java.util.List;

public class Main {

    private static final UserService key = new UserServiceImpl();
    private static final User user1 = new User("Tom", "Clark", (byte)35);
    private static final User user2 = new User("Mary", "Gold", (byte)28);
    private static final User user3 = new User("Alice", "Smith", (byte)38);
    private static final User user4 = new User("Ronald", "White", (byte)23);

    public static void main(String[] args) {

        key.createUsersTable();

        key.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        key.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        key.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        key.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        List<User> users = key.getAllUsers();
        for (User i : users) {
            System.out.println(i);
        }

        key.removeUserById(2);

        key.cleanUsersTable();

        key.dropUsersTable();

        Util.getSessionFactory().close();

    }
}
