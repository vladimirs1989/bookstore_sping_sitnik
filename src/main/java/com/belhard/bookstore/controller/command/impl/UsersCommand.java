package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class UsersCommand implements Command {

    private static final UserService USER_SERVICE = new UserServiceImpl();

    public String execute(HttpServletRequest req) {
        List<UserDto> users = USER_SERVICE.getAllUser();
        req.setAttribute("users", users);
        return "jsp/users.jsp";
    }
}