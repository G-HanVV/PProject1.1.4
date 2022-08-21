package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static int USER_COUNT = 0;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables("PP_1_1_3-4_JDBC_Hibernate", null, "USERS",
                new String[]{"TABLE"});
        if (!tables.first()) {
//            System.out.println("Create new table");
            String sql = "CREATE TABLE USERS " +
                    "(id INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " last_name VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            statement.executeUpdate(sql);
        } else {
//            System.out.println("Table already exists");
        }
        statement.close();
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables("PP_1_1_3-4_JDBC_Hibernate"
                , null
                , "USERS"
                , new String[]{"TABLE"});
        if (tables.first()) {
            String sql = "DROP TABLE USERS ";
            statement.executeUpdate(sql);
//            System.out.println("Table USERS is dropped");
        } else {
//            System.out.println("No such table exists in the database");
        }
        statement.close();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO USERS " +
                "VALUES (" + (++USER_COUNT) + ", '" + name + "', '" + lastName + "', " + (int) age + ")";
        statement.executeUpdate(sql);
        statement.close();
        System.out.println("User " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        String sql = "DELETE FROM USERS " +
                "WHERE id = " + id;
        statement.executeUpdate(sql);
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        int i = 0;
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT id, name, last_name, age FROM USERS";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            userList.add(new User(resultSet.getString("name")
                    , resultSet.getString("last_name")
                    , (byte) resultSet.getInt("age")));
            userList.get(i).setId((long) resultSet.getInt("id"));
            i++;
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        String sql = "DELETE FROM USERS";
        statement.executeUpdate(sql);
    }
}
