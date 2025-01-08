package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS user" +
            "(id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(15), " +
            "last_name VARCHAR(15), " +
            "age INT)";
    private final String DROP_TABLE = "DROP TABLE IF EXISTS user";



    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE_QUERY).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(DROP_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            } else {
                System.out.println("User NOT FOUND");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("FROM User", User.class).list();
            transaction.commit();
            System.out.println("Получены все пользователи");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}

