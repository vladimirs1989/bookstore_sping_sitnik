package com.belhard.bookstore;

import com.belhard.bookstore.controller.command.impl.BookCommand;
import com.belhard.bookstore.controller.command.impl.BooksCommand;
import com.belhard.bookstore.controller.command.impl.ErrorCommand;
import com.belhard.bookstore.controller.command.impl.UserCommand;
import com.belhard.bookstore.controller.command.impl.UsersCommand;
import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.impl.BookDaoJdbcImpl;
import com.belhard.bookstore.dao.impl.UserDaoJdbcImpl;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ContextConfiguration {
    @Bean
    public UserDao userDao(){
        return new UserDaoJdbcImpl();
    }

    @Bean
    public UserService userService(){
        return  new UserServiceImpl(userDao());
    }

    @Bean
    public UserCommand userCommand(){
        return  new UserCommand(userService());
    }

    @Bean
    public UsersCommand usersCommand(){
        return  new UsersCommand(userService());
    }

    @Bean
    public BookDao bookDao(){
        return new BookDaoJdbcImpl();
    }

    @Bean
    public BookService bookService(){
        return  new BookServiceImpl(bookDao());
    }

    @Bean
    public BookCommand bookCommand(){
        return  new BookCommand(bookService());
    }

    @Bean
    public BooksCommand booksCommand(){
        return  new BooksCommand(bookService());
    }

    @Bean
    public ErrorCommand errorCommand(){
        return  new ErrorCommand(bookService());
    }


}