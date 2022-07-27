package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    public static Connection getSQLConnection() {
        String userName = "root";
        String password = "myroot123";
        String connectionURL = "jdbc:mysql://localhost:3306/my_db_113";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(connectionURL, userName,
                    password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Ошибка при подключении к БД!");
        }
        return null;
    }

    public static Connection closeSQLConnection() {
        String userName1 = "root";
        String password1 = "myroot123";
        String connectionURL1 = "jdbc:mysql://localhost:3306/my_db_113";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.getConnection(connectionURL1, userName1,
                    password1).close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Ошибка при закрытии БД!");
        }
        return null;
    }
}

