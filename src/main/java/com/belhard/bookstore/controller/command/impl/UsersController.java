package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UsersController  {

    private static UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
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


    @GetMapping
    public String execute(Model model, @PageableDefault(sort = {"lastName", "firstName"}, direction =  Sort.Direction.ASC) Pageable pageable) {
        List<UserDto> users = userService.getAllUser(pageable);
        model.addAttribute("users", users);
        model.addAttribute("page", pageable);
        return "users";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Model model, @RequestParam Map<String, Object> params) {
        UserDto userDto = new UserDto();
        userDto.setLastName(params.get("lastName").toString());
        userDto.setFirstName(params.get("firstName").toString());
        userDto.setEmail(params.get("email").toString());
        userDto.setLogin(params.get("login").toString());
        userDto.setPassword(params.get("password").toString());
        userDto.setAge(Integer.valueOf(params.get("age").toString()));
        userDto.setRole(UserDto.RoleDto.valueOf(params.get("role").toString()));
        UserDto created = userService.createUser(userDto);
        model.addAttribute("user", created);
        return "user";
    }

    @PostMapping("/{id}")
    public String update(Model model, @PathVariable Long id, @RequestParam Map<String, Object> params) {
        UserDto userDto = userService.getUserById(id);
        userDto.setLastName(params.get("lastName").toString());
        userDto.setFirstName(params.get("firstName").toString());
        userDto.setEmail(params.get("email").toString());
        userDto.setLogin(params.get("login").toString());
        userDto.setPassword(params.get("password").toString());
        userDto.setAge(Integer.valueOf(params.get("age").toString()));
        userDto.setRole(UserDto.RoleDto.valueOf(params.get("role").toString()));
        UserDto updated = userService.updateUser(userDto);
        model.addAttribute("user", updated);
        return "user";
    }

    @PostMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id) {
        userService.deleteUser(id);
        model.addAttribute("message", "user with id = " + id + " is deleted");
        return "delete";
    }

    @GetMapping("/edit/{id}")
    public String editForm(Model model, @PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        model.addAttribute("user", userDto);
        return "userUpdate";
    }

    @GetMapping("/create")
    public String createForm() {
        return "userCreate";
    }
}