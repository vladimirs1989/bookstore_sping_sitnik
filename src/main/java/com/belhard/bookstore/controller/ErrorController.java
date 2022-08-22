package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/error")
public class ErrorController  {

    private static BookService bookService;

    @Autowired
    public ErrorController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String execute(Model model) {

        model.addAttribute("message", "Ooops");
        return "error";
    }
}
