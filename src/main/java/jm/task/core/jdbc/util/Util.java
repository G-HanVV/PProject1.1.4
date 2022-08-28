package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util implements AutoCloseable{
    // реализуйте настройку соединения с БД
    private static Connection connection;
    private static SessionFactory sessionFactory;
    private static Session session;

    public static Session getSession(){
        if (session == null || !session.isOpen()) {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
            session = sessionFactory.getCurrentSession();
        }
        return session;
    }
    public static void closeSession(){
        if (session != null) {
            session.close();
            sessionFactory.close();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
//            System.out.println("\tUtil. Start opening connection");
            String url = "jdbc:mysql://localhost/PP_1_1_3-4_JDBC_Hibernate";
            String user = "root";
            String pass = "root";
            connection = DriverManager.getConnection(url, user, pass);
        } else {
//            System.out.println("\tUtil. Connection is opened");
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public void close() throws SQLException {
        if (session != null) {
            session.close();
            sessionFactory.close();
        }
    }
}
