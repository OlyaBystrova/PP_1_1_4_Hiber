package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class Util {
    static String userName = "root";
    static String password = "myroot123";
    static String connectionURL = "jdbc:mysql://localhost:3306/my_db_113";
    static Connection conn = null;

    public static Connection getSQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return conn = DriverManager.getConnection(connectionURL, userName,
                    password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Ошибка при подключении к БД!");
        }
        return null;
    }
    public static Connection closeSQLConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при закрытии БД!");
        }
        return null;
    }


    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                // Конфигурация источника данных
                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, connectionURL);// "jdbc:mysql://localhost:3306/my_db_113");
                properties.put(Environment.USER, userName); //"root");
                properties.put(Environment.PASS, password); //"myroot123");

                // Конфигурация Hibernate
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.put(Environment.SHOW_SQL, true);
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.addAnnotatedClass(User.class);
                configuration.setProperties(properties);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (HibernateException e) {
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }
}



