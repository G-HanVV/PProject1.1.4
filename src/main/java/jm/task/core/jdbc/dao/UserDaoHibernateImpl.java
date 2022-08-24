package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.*;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables("PP_1_1_3-4_JDBC_Hibernate", null, "USERS",
                    new String[]{"TABLE"});
            if (!tables.first()) {
                System.out.println("\nCreate new table");
                Session session = Util.getSession();
                session.beginTransaction();
                session.createSQLQuery("create table Users (" +
                                "id bigint not null auto_increment, " +
                                "age tinyint, " +
                                "last_name varchar(255), " +
                                "name varchar(255), " +
                                "primary key (id))")
                        .executeUpdate();
                session.getTransaction().commit();
                Util.closeSession();
            } else {
                System.out.println("\nTable already exists");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createSQLQuery("drop table if exists Users").executeUpdate();
        session.getTransaction().commit();
        Util.closeSession();
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = Util.getSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        Util.closeSession();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
        Util.closeSession();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSession();
        session.beginTransaction();
        List<User> userList = session.createQuery("From User").list();
        session.getTransaction().commit();
        Util.closeSession();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        session.getTransaction().commit();
        Util.closeSession();
    }
}
