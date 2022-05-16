package com.belhard.bookstore.controller.command;

import com.belhard.bookstore.controller.Controller;
import com.belhard.bookstore.controller.command.impl.BookCommand;
import com.belhard.bookstore.controller.command.impl.BooksCommand;
import com.belhard.bookstore.controller.command.impl.ErrorCommand;
import com.belhard.bookstore.controller.command.impl.UserCommand;
import com.belhard.bookstore.controller.command.impl.UsersCommand;
import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.impl.UserDaoJdbcImpl;
import com.belhard.bookstore.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {


    private static class Holder{
        private static final CommandFactory instance = new CommandFactory();
    }

    public static  CommandFactory getInstance(){
        return Holder.instance;
    }
    private CommandFactory(){

    }

    private  static  final Map<String,Command> map = new HashMap<>();

    static {
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        map.put("book", new BookCommand());
        map.put("books", new BooksCommand());
        map.put("user", Controller.context.getBean("userCommand", UserCommand.class));
        map.put("users", Controller.context.getBean("usersCommand", UsersCommand.class));
        map.put("error", new ErrorCommand());
    }

    public Command getCommand(String action){
        Command command = map.get(action);
        if(command == null){
            return map.get("error");
        }
        return  command;
    }
}
