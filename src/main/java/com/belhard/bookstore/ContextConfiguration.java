package com.belhard.bookstore;

import com.belhard.bookstore.controller.command.impl.UsersCommand;
import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.impl.UserDaoJdbcImpl;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ContextConfiguration {



}
