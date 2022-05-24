package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("/users")
public class UsersCommand implements Command {

    private static UserService userService;

    @Autowired
    public UsersCommand(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String execute(Model model) {
        List<UserDto> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "users";
    }
}