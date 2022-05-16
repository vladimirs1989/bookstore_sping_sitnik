package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.dbconfig.DbConfigurator;
import com.belhard.bookstore.dao.entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJdbcImpl implements BookDao {

    private static final Logger logger = LogManager.getLogger(BookDaoJdbcImpl.class);

    public static final String GET_ALL = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.deleted = false ORDER BY b.id";
    public static final String GET_BOOK_BY_ID = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.id = ? AND b.deleted = false ";
    public static final String GET_BOOK_BY_ISBN = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.isbn = ? AND b.deleted = false ";
    public static final String GET_BOOK_BY_AUTHOR = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.author = ? AND b.deleted = false ";
    public static final String CREATE_BOOK = "INSERT INTO books (isbn, title, author, pages, cover_id, price)  VALUES ( ?, ?, ?, ?, (SELECT id FROM covers WHERE name = ?) ,?)";
    public static final String UPDATE_BOOK = "UPDATE books SET isbn = ?, title = ?, author = ?, pages = ?, cover_id = (SELECT id FROM covers WHERE name = ?), price = ?  WHERE id = ? AND deleted = false";
    public static final String DELETE_BOOK = "UPDATE books SET deleted = true WHERE id = ? AND deleted = false";
    public static final String COUNT_BOOK = "SELECT COUNT(*) AS count FROM books WHERE deleted = false";

    public BookDaoJdbcImpl() {
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            logger.debug("Database query GET_ALL");
            while (resultSet.next()) {
                Book book = processResultSet(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            logger.error("error in method - getAllBooks");
        }
        return books;
    }

    private Book processResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPages(resultSet.getInt("pages"));
        book.setCover(Book.Cover.valueOf(resultSet.getString("cover")));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }

    public Book getBookById(Long id) {
        Book book = null;
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(GET_BOOK_BY_ID);
            logger.debug("Database query GET_BOOK_BY_ID");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = processResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("error in method - getBookById");
        }
        return book;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        Book book = null;
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(GET_BOOK_BY_ISBN);
            logger.debug("Database query GET_BOOK_BY_ISBN");
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book = processResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("error in method - getBookByIsbn");
        }
        return book;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(GET_BOOK_BY_AUTHOR);
            logger.debug("Database query GET_BOOK_BY_AUTHOR");
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = processResultSet(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            logger.error("error in method - getBooksByAuthor");
        }
        return books;
    }

    @Override
    public Book createBook(Book book) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(CREATE_BOOK, Statement.RETURN_GENERATED_KEYS);
            logger.debug("Database query CREATE_BOOK");
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getPages());
            statement.setString(5, book.getCover().toString());
            statement.setBigDecimal(6, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(("id"));
                return getBookById(id);
            }
        } catch (SQLException e) {
            logger.error("error in method - createBook");
        }
        throw new RuntimeException("Fail in create");
    }

    @Override
    public Book updateBook(Book book) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(UPDATE_BOOK);
            logger.debug("Database query UPDATE_BOOK");
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setLong(4, book.getId());
            statement.setString(5, book.getCover().toString());
            statement.setBigDecimal(6, book.getPrice());
            statement.setLong(7, book.getId());
            statement.executeUpdate();
            return getBookById(book.getId());
        } catch (SQLException e) {
            logger.error("error in method - updateBook");
        }
        throw new RuntimeException("fail update");
    }

    @Override
    public boolean deleteBook(Long id) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(DELETE_BOOK);
            logger.debug("Database query DELETE_BOOK");
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            logger.error("error in method - deleteBook");
        }
        return false;
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
