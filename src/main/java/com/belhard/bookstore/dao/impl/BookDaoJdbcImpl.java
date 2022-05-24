package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.mapper.BookRowMapper;
import com.belhard.bookstore.dao.dbconfig.DbConfigurator;
import com.belhard.bookstore.dao.entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("bookDao")
public class BookDaoJdbcImpl implements BookDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BookRowMapper rowMapper;

    @Autowired
    public BookDaoJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate, BookRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    private static final Logger logger = LogManager.getLogger(BookDaoJdbcImpl.class);

    public static final String GET_ALL = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.deleted = false ORDER BY b.id";
    public static final String GET_BOOK_BY_ID = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.id = :id AND b.deleted = false ";
    public static final String GET_BOOK_BY_ISBN = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.isbn = :isbn AND b.deleted = false ";
    public static final String GET_BOOK_BY_AUTHOR = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.author = :author AND b.deleted = false ";
    public static final String CREATE_BOOK = "INSERT INTO books (isbn, title, author, pages, cover_id, price)  VALUES ( :isbn, :title, :author, :pages, (SELECT id FROM covers WHERE name = :cover) ,:price)";
    public static final String UPDATE_BOOK = "UPDATE books SET isbn = :isbn, title = :title, author = :author, pages = :pages, cover_id = (SELECT id FROM covers WHERE name = :cover), price = :price  WHERE id = :id AND deleted = false";
    public static final String DELETE_BOOK = "UPDATE books SET deleted = true WHERE id = :id AND deleted = false";
    public static final String COUNT_BOOK = "SELECT COUNT(*) AS count FROM books WHERE deleted = false";

    public List<Book> getAllBooks() {
        return jdbcTemplate.query(GET_ALL, rowMapper);
    }

    public Book getBookById(Long id) {
        return jdbcTemplate.queryForObject(GET_BOOK_BY_ID, Map.of("id", id), rowMapper);
        //logger.debug("Database query GET_BOOK_BY_ID");
        //logger.error("error in method - getBookById");
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return jdbcTemplate.queryForObject(GET_BOOK_BY_ISBN, Map.of("isbn", isbn), rowMapper);
        //logger.debug("Database query GET_BOOK_BY_ISBN");
        //logger.error("error in method - getBookByIsbn");

    }

    @Override
    public List<Book> getBooksByAuthor(String author) {

        return jdbcTemplate.query(GET_BOOK_BY_AUTHOR, Map.of("author", author), rowMapper);
        //logger.debug("Database query GET_BOOK_BY_AUTHOR");
        //logger.error("error in method - getBooksByAuthor");
    }

    @Override
    public Book createBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("isbn", book.getIsbn());
        params.put("title", book.getTitle());
        params.put("author", book.getAuthor());
        params.put(" pages", book.getPages());
        params.put("cover", book.getCover().toString().toLowerCase());
        params.put("price", book.getPrice());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdated = jdbcTemplate.update(CREATE_BOOK, source, keyHolder, new String[]{"id"});
        if (rowsUpdated != 1) {
            throw new RuntimeException("Can't create book: " + book);
        }
        Long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new RuntimeException("Can't create book: " + book));
        return getBookById(id);
        //logger.debug("Database query CREATE_BOOK");
        //logger.error("error in method - createBook");
    }

    @Override
    public Book updateBook(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("isbn", book.getIsbn());
        params.put("title", book.getTitle());
        params.put("author", book.getAuthor());
        params.put(" pages", book.getPages());
        params.put("cover", book.getCover().toString().toLowerCase());
        params.put("price", book.getPrice());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdated = jdbcTemplate.update(UPDATE_BOOK, source);
        if (rowsUpdated != 1) {
            throw new RuntimeException("Can't update book: " + book);
        }
        return getBookById(book.getId());
        //logger.debug("Database query UPDATE_BOOK");
        //logger.error("error in method - updateBook");
    }

    @Override
    public boolean deleteBook(Long id) {
        int result = jdbcTemplate.update(DELETE_BOOK, Map.of("id", id));
        if (result != 1) {
            throw new RuntimeException("Can't delete book with id: " + id);
        }
        return true;
        //logger.debug("Database query DELETE_BOOK");
        //logger.error("error in method - deleteBook");
    }

    public int countAllBooks() {
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_BOOK);
            logger.debug("Database query COUNT_BOOK");
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("error in method - countAllBooks");
        }
        throw new RuntimeException("books not count");
    }
}
