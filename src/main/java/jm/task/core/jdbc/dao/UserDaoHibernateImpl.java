package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.ReportCollector;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;

import javax.persistence.PersistenceException;
import java.sql.*;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession()){
            session.beginTransaction();
            int r = Integer.parseInt(session.createSQLQuery("SELECT COUNT(*) FROM information_schema.TABLES WHERE" +
                    "  TABLE_NAME =\"USERS\"").getSingleResult().toString());
            if (r == 1) {
                session.createSQLQuery("create table Users (" +
                                "id bigint not null auto_increment, " +
                                "age tinyint, " +
                                "last_name varchar(255), " +
                                "name varchar(255), " +
                                "primary key (id))")
                        .executeUpdate();
                session.getTransaction().commit();
                ReportCollector.toReport("Table created");
            } else {
                ReportCollector.toReport("Table already exists");
            }
        } catch (PersistenceException e) {
            Util.getSession().getTransaction().rollback();
            System.out.println(" --- " + e + " --- ");
            ReportCollector.toReport(e.toString());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession()){
            session.beginTransaction();
            session.createSQLQuery("drop table if exists Users").executeUpdate();
            ReportCollector.toReport("Table Users dropped");
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            Util.getSession().getTransaction().rollback();
            System.out.println(" --- " + e + " --- ");
            ReportCollector.toReport(e.toString());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession()){
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("???????????????????????? " + name + " ???????????????? ?? ??????????????");
            ReportCollector.toReport("???????????????????????? " + name + " ???????????????? ?? ??????????????");
        } catch (PersistenceException e) {
            Util.getSession().getTransaction().rollback();
            System.out.println(" --- " + e + " --- ");
            ReportCollector.toReport(e.toString());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession()){
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                ReportCollector.toReport("User deleted from table by id = " + id);
            } else {
                ReportCollector.toReport("No user with this id = " + id);
            }
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            Util.getSession().getTransaction().rollback();
            System.out.println(" --- " + e + " --- ");
            ReportCollector.toReport(e.toString());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSession()){
            session.beginTransaction();
            List<User> userList = session.createQuery("From User").list();
            session.getTransaction().commit();
            return userList;
        } catch (PersistenceException e) {
            Util.getSession().getTransaction().rollback();
            System.out.println(" --- " + e + " --- ");
            ReportCollector.toReport(e.toString());
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            ReportCollector.toReport("Table Users cleared");
        } catch (PersistenceException e) {
            System.out.println(" --- " + e + " --- ");
            ReportCollector.toReport(e.toString());
        }
    }
}
