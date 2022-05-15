package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class UserCommand implements Command {
    private static final UserService USER_SERVICE = new UserServiceImpl();

    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        UserDto userDto = USER_SERVICE.getUserById(id);
        if (userDto == null) {
            req.setAttribute("message", "No user with id: " + id);
            return "jsp/error.jsp";
        }
        req.setAttribute("user", userDto);
        return "jsp/user.jsp";
    }
}
