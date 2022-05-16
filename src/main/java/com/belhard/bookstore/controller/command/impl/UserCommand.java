package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("userCommand")
public class UserCommand implements Command {

    @Autowired
    private static UserService USER_SERVICE;

    public UserCommand(UserService userService) {
        this.USER_SERVICE = userService;
    }

    public static void setUserService(UserService userService) {
        USER_SERVICE = userService;
    }

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
