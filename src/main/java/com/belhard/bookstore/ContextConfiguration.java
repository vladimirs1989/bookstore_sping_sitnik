package com.belhard.bookstore;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.impl.BookDaoJdbcImpl;
import com.belhard.bookstore.dao.impl.OrderDaoJdbcImpl;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
public class ContextConfiguration {
    public static void main(String[] args) {
        SpringApplication.run(ContextConfiguration.class, args);
    }

}
