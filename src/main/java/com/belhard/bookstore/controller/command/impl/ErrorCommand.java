package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {
    private static BookService BOOK_SERVICE;

    public ErrorCommand(BookService bookService) {
        this.BOOK_SERVICE = bookService;
    }

    public String execute(HttpServletRequest req) {

        req.setAttribute("message", "Ooops");
        return "jsp/error.jsp";
    }
}