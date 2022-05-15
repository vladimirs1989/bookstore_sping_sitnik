package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {
    private static final BookService BOOK_SERVICE = new BookServiceImpl();

    public String execute(HttpServletRequest req) {

            req.setAttribute("message", "Ooops");
            return "jsp/error.jsp";
    }
}
