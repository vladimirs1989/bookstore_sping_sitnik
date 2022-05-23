package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/error")
public class ErrorCommand {

    private static BookService bookService;
    @Autowired
    public ErrorCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String execute(Model model) {

        model.addAttribute("message", "Ooops");
        return "error";
    }
}
