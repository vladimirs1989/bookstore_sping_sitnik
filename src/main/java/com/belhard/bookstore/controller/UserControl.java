/*
package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public class UserControl {

    private static final UserService USER_SERVICE = new UserServiceImpl();
    public static final String HELP = "Reference: \n" +
            "all - see all users \n" +
            "get id - view selected user by Id \n" +
            "getByEmail email - view selected user by Email \n" +
            "getByLastname lastname - view selected user by lastname \n" +
            "create - create user \n" +
            "update - update user \n" +
            "count - number of users  \n" +
            "delete id - delete selected user \n" +
            "exit - exit\n" +
            "enter the command: ";

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        while (workWithUser( scanner)) {

        }
    }

    private static boolean workWithUser(Scanner scanner) {
        System.out.println(HELP);
        boolean programmIsActive = true;
        String key = scanner.next();
        switch (key) {
            case "all": {
                List<UserDto> users = USER_SERVICE.getAllUser();
                users.forEach(System.out::println);
                break;
            }
            case "get": {
                String key1 = scanner.next();
                UserDto user = USER_SERVICE.getUserById(Long.valueOf(key1));
                System.out.println("User: ");
                System.out.println(user);
                break;
            }
            case "getByEmail": {
                String email = scanner.next();
                UserDto user = USER_SERVICE.getUserByEmail(email);
                System.out.println("Users: ");
                System.out.println(user);
                break;
            }
            case "getByLastName": {
                String lastName = scanner.next();
                List<UserDto> users = USER_SERVICE.getUserByLastname(lastName);
                users.forEach(System.out::println);
                break;
            }
            case "create": {
                UserDto user = new UserDto();
                System.out.println("");
                System.out.println("enter last name");
                user.setLastName(scanner.next());
                System.out.println("enter first name");
                user.setFirstName(scanner.next());
                System.out.println("enter email");
                user.setEmail(scanner.next());
                System.out.println("enter login");
                user.setLogin(scanner.next());
                System.out.println("enter password");
                user.setPassword(scanner.next());
                System.out.println("enter age");
                user.setAge(scanner.nextInt());
                System.out.println("enter role");
                user.setRole(UserDto.RoleDto.valueOf(scanner.next()));
                user = USER_SERVICE.createUser(user);
                System.out.println(user);
                break;
            }
            case "update": {
                UserDto user = new UserDto();
                System.out.println("");
                System.out.println("enter last name");
                user.setLastName(scanner.next());
                System.out.println("enter first name");
                user.setFirstName(scanner.next());
                System.out.println("enter email");
                user.setEmail(scanner.next());
                System.out.println("enter login");
                user.setLogin(scanner.next());
                System.out.println("enter password");
                user.setPassword(scanner.next());
                System.out.println("enter age");
                user.setAge(scanner.nextInt());
                System.out.println("enter role");
                user.setRole(UserDto.RoleDto.valueOf(scanner.next()));
                System.out.println("enter id");
                user.setId(scanner.nextLong());
                user = USER_SERVICE.updateUser(user);
                System.out.println(user);
                break;
            }
            case "delete": {
                String key1 = scanner.next();
                Long id = Long.valueOf(key1);
                USER_SERVICE.deleteUser(id);
                System.out.println("User with id: " + id + " deleted successfully");
                break;
            }
            case "count":
                System.out.println("count of users " + USER_SERVICE.countAllUsers());
                break;
            case "exit":
                programmIsActive = false;
                break;
            default:
                System.out.println("command not find");
                break;
        }
        return programmIsActive;
    }
}
*/
