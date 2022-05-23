package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/book")
public class BookCommand {

    private static BookService bookService;

    @Autowired
    public BookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public String execute(Model model, @PathVariable Long id) {
        BookDto bookDto = bookService.getBookById(id);
        if (bookDto == null) {
            model.addAttribute("message", "No book with id: " + id);
            return "error";
        }
        model.addAttribute("book", bookDto);
        return "book";
    }
}
