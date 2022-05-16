package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class UsersCommand implements Command {

    private static UserService USER_SERVICE;

    public UsersCommand(UserService userService) {
        this.USER_SERVICE = userService;
    }

    public static void setUserService(UserService userService) {
        USER_SERVICE = userService;
    }

    public String execute(HttpServletRequest req) {
        List<UserDto> users = USER_SERVICE.getAllUser();
        req.setAttribute("users", users);
        return "jsp/users.jsp";
    }
}