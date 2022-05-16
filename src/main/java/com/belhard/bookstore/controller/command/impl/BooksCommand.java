package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("booksCommand")
public class BooksCommand implements Command {

    @Autowired
    private static BookService BOOK_SERVICE;

    public BooksCommand(BookService bookService) {
        this.BOOK_SERVICE = bookService;
    }

    public String execute(HttpServletRequest req) {
        List<BookDto> books = BOOK_SERVICE.getAllBooks();
        req.setAttribute("books", books);
        return "jsp/books.jsp";
    }
}