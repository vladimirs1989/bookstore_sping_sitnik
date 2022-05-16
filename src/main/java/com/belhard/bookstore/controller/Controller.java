package com.belhard.bookstore.controller;

import com.belhard.bookstore.ContextConfiguration;
import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.controller.command.CommandFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    public static AnnotationConfigApplicationContext context;

    @Override
    public void init() throws ServletException {
        context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("command");
        Command command = CommandFactory.getInstance().getCommand(action);
        String page = command.execute(req);
        req.getRequestDispatcher(page).forward(req, resp);
    }
}
