package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("/users")
public class UsersController implements Command {

    private static UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String execute(Model model) {
        List<UserDto> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user/{id}")
    public String execute(Model model, @PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        if (userDto == null) {
            model.addAttribute("message", "No user with id: " + id);
            return "error";
        }
        model.addAttribute("user", userDto);
        return "user";
    }
}