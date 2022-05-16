package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("bookCommand")
public class BookCommand implements Command {
    @Autowired
    private static BookService BOOK_SERVICE;

    public BookCommand(BookService bookService) {
        this.BOOK_SERVICE = bookService;
    }

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
