package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserService userService = new UserServiceImpl();

        userService.dropUsersTable();

        userService.createUsersTable();

        userService.saveUser("Булат", "Гилимханов", (byte)33);
        userService.saveUser("Анна", "Агеенко", (byte)20);
        userService.saveUser("Сергей", "Зубарьков", (byte)34);
        userService.saveUser("Гульназ", "Амерханова", (byte)26);
        List<User> userList = userService.getAllUsers();
        ReportCollector.toReport(userList.toString());

        userService.removeUserById(2);
        List<User> userList2 = userService.getAllUsers();
        ReportCollector.toReport(userList2.toString());

        userService.cleanUsersTable();
        List<User> userList3 = userService.getAllUsers();
        ReportCollector.toReport(userList3.toString());

        userService.dropUsersTable();

        ReportCollector.printReport();
    }
}
