package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getSQLConnection();
    private final String sqlTable = "user";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("CREATE TABLE IF NOT EXISTS `my_db_113`.`" + sqlTable + "` ( " +
                        " `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY , " +
                        " `name` VARCHAR(50) NOT NULL, " +
                        " `lastName` VARCHAR(50) NOT NULL, " +
                        " `age` INT NOT NULL);")) {
            preparedStatement.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("При создании таблицы что-то пошло не так");
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS user")){
            preparedStatement.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("При тестировании удаления таблицы что-то пошло не так");
        }
        try {
            connection.rollback();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO user (name, lastName, age) VALUES (?,?,?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных ");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("При добавлении пользователя в таблицу что-то пошло не так");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id=?")){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
            System.out.println("User с id " + id + " удален из базы данных");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("При удалении пользователя по id что-то пошло не так");
        }
        try {
            connection.rollback();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()){
            String SQL = "SELECT * FROM user";
            ResultSet resultSet = statement.executeQuery(SQL);
            connection.setAutoCommit(false);
            connection.commit();

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user")) {
            preparedStatement.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("При очистке таблицы что-то пошло не так");
        }
        try {
            connection.rollback();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
