package com.belhard.bookstore.controller;

import com.belhard.bookstore.ContextConfiguration;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    public static AnnotationConfigApplicationContext context;

    @PostConstruct
    @Override
    public void init() {
        context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
    }

    @PreDestroy
    @Override
    public void destroy() {
        context.close();
    }

    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }*/

   /* private static final Map<String, Command> map = new HashMap<>();

    static {
        map.put("book", context.getBean("bookCommand", BookCommand.class));
        map.put("books", context.getBean("booksCommand", BooksController.class));
        map.put("user", context.getBean("userCommand", UserCommand.class));
        map.put("users", context.getBean("usersCommand", UsersController.class));
        map.put("error", context.getBean("errorCommand", ErrorCommand.class));
    }

    public Command getCommand(String action) {
        Command command = map.get(action);
        if (command == null) {
            return map.get("error");
        }
        return command;
    }*/

    /*private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("command");
        //Command command = getCommand(action);
        Command command = context.getBean(action,Command.class);
        String page = command.execute(req);
        req.getRequestDispatcher(page).forward(req, resp);
    }*/
}
