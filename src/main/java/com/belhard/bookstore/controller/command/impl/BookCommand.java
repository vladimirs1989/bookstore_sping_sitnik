package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class BookCommand implements Command {
    private static final BookService BOOK_SERVICE = new BookServiceImpl();

    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        BookDto bookDto = BOOK_SERVICE.getBookById(id);
        if (bookDto == null) {
            req.setAttribute("message", "No book with id: " + id);
            return "jsp/error.jsp";

        }
        req.setAttribute("book", bookDto);
        return "jsp/book.jsp";
    }
}
