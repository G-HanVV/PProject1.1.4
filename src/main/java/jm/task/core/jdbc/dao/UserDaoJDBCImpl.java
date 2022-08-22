package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables("PP_1_1_3-4_JDBC_Hibernate", null, "USERS",
                    new String[]{"TABLE"});
            if (!tables.first()) {
                System.out.println("\nCreate new table");
                String sql = "CREATE TABLE USERS " +
                        "(id INTEGER not NULL AUTO_INCREMENT, " +
                        " name VARCHAR(255), " +
                        " last_name VARCHAR(255), " +
                        " age INTEGER, " +
                        " PRIMARY KEY ( id ))";
                statement.executeUpdate(sql);
            } else {
                System.out.println("\nTable already exists");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables("PP_1_1_3-4_JDBC_Hibernate"
                    , null
                    , "USERS"
                    , new String[]{"TABLE"});
            if (tables.first()) {
                String sql = "DROP TABLE USERS ";
                statement.executeUpdate(sql);
                System.out.println("\nTable USERS is dropped");
            } else {
                System.out.println("\nNo such table exists in the database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO USERS (name, last_name, age)" +
                    "VALUES ('" + name + "', '" + lastName + "', " + (int) age + ")";
            statement.executeUpdate(sql);
            statement.close();
            System.out.println("User " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM USERS " +
                    "WHERE id = " + id;
            statement.executeUpdate(sql);
            System.out.println("\nUser deleted from table by id - " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        int i = 0;
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "SELECT id, name, last_name, age FROM USERS";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("name")
                        , resultSet.getString("last_name")
                        , (byte) resultSet.getInt("age")));
                userList.get(i++).setId((long) resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM USERS";
            statement.executeUpdate(sql);
            System.out.println("\nThe table cleared");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
