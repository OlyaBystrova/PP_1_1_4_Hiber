package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction = null;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String table = "CREATE TABLE IF NOT EXISTS user"
                + "(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name CHAR(50) NOT NULL, " +
                "lastName CHAR(50) NOT NULL," +
                "age INT)";
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(table).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("При создании таблицы что-то пошло не так");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            System.out.println("Возникла ошибка при удалении таблицы");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных ");
        } catch (Exception e) {
            System.out.println("Возникла ошибка при добавлении пользователя");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("User с id " + id + " удален из базы данных");
    } catch (HibernateException e) {
            System.out.println("Возникла ошибка при удалении пользователя по id");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

        @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            System.out.println("Получен список пользователей.");
            return session.createQuery("FROM User").list();
        } catch (Exception e) {
            System.out.println("Возникла ошибка при получении списка пользователей");
        }
            return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM user").executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            System.out.println("Возникла ошибка при очистке таблицы");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
