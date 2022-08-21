package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Булат", "Гилимханов", (byte)33);
        userService.saveUser("Анна", "Агеенко", (byte)20);
        userService.saveUser("Сергей", "Зубарьков", (byte)34);
        userService.saveUser("Гульназ", "Амерханова", (byte)26);
        List<User> userList = userService.getAllUsers();
        System.out.println("\n" + userList);
        userService.removeUserById(3);
        List<User> userList2 = userService.getAllUsers();
        System.out.println("\n" + userList2);
        userService.cleanUsersTable();
        List<User> userList3 = userService.getAllUsers();
        System.out.println("\n" + userList3);
//        userService.dropUsersTable();
        Util.closeConnection();
    }
}
