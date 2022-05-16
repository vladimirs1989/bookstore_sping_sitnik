package com.belhard.bookstore.controller.command;

import com.belhard.bookstore.controller.Controller;
import com.belhard.bookstore.controller.command.impl.BookCommand;
import com.belhard.bookstore.controller.command.impl.BooksCommand;
import com.belhard.bookstore.controller.command.impl.ErrorCommand;
import com.belhard.bookstore.controller.command.impl.UserCommand;
import com.belhard.bookstore.controller.command.impl.UsersCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {


    private static class Holder {
        private static final CommandFactory instance = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return Holder.instance;
    }

    private CommandFactory() {
    }

    private static final Map<String, Command> map = new HashMap<>();

    static {
        map.put("book", Controller.context.getBean("bookCommand", BookCommand.class));
        map.put("books", Controller.context.getBean("booksCommand", BooksCommand.class));
        map.put("user", Controller.context.getBean("userCommand", UserCommand.class));
        map.put("users", Controller.context.getBean("usersCommand", UsersCommand.class));
        map.put("error", Controller.context.getBean("errorCommand", ErrorCommand.class));
    }

    public Command getCommand(String action) {
        Command command = map.get(action);
        if (command == null) {
            return map.get("error");
        }
        return command;
    }
}