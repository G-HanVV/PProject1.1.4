package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args){
        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        System.out.println();
        userService.saveUser("Булат", "Гилимханов", (byte)33);
        userService.saveUser("Анна", "Агеенко", (byte)20);
        userService.saveUser("Сергей", "Зубарьков", (byte)34);
        userService.saveUser("Гульназ", "Амерханова", (byte)26);

        List<User> userList = userService.getAllUsers();

        System.out.println(userList);

        userService.removeUserById(2);

        List<User> userList2 = userService.getAllUsers();

        System.out.println(userList2);

        userService.cleanUsersTable();

        List<User> userList3 = userService.getAllUsers();

        System.out.println(userList3);

        userService.dropUsersTable();

    }
}
