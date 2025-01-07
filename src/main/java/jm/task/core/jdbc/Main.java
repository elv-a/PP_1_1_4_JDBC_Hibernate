package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Elvina", "Ismak", (byte) 21);
        userService.saveUser("Elvin", "Ismakov", (byte) 21);
        userService.saveUser("Adilya", "Ismak", (byte) 16);
        userService.saveUser("Nailya", "Ahmet", (byte) 20);

        userService.removeUserById(1);
        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
