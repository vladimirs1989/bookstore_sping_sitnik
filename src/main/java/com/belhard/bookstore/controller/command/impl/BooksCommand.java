package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("/books")
public class BooksCommand implements Command {

    private static BookService bookService;

    @Autowired
    public BooksCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String execute(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }
}