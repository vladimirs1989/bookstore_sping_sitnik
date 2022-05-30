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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("bookDao")
public class BookDaoJdbcImpl implements BookDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BookRowMapper rowMapper;

    @PersistenceContext
    private EntityManager manager;

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

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = manager.createQuery("from Book",Book.class).getResultList();
        manager.clear();
        return books;
        //return jdbcTemplate.query(GET_ALL, rowMapper);
    }

    @Override
    public Book getBookById(Long id) {
        Book book = manager.find(Book.class, id);
        manager.clear();
        return book;
        //return jdbcTemplate.queryForObject(GET_BOOK_BY_ID, Map.of("id", id), rowMapper);
        //logger.debug("Database query GET_BOOK_BY_ID");
        //logger.error("error in method - getBookById");
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        Book book = manager.find(Book.class, isbn);
        manager.clear();
        return book;
        //return jdbcTemplate.queryForObject(GET_BOOK_BY_ISBN, Map.of("isbn", isbn), rowMapper);
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
        manager.getTransaction().begin();
        manager.persist(book);
        manager.getTransaction().commit();
        manager.clear();
        return book;
        /*KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("isbn", book.getIsbn());
        params.put("title", book.getTitle());
        params.put("author", book.getAuthor());
        params.put("pages", book.getPages());
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
        return getBookById(id);*/
        //logger.debug("Database query CREATE_BOOK");
        //logger.error("error in method - createBook");
    }

    @Override
    public Book updateBook(Book book) {
        manager.getTransaction().begin();
        manager.merge(book);
        manager.getTransaction().commit();
        manager.clear();
        return book;
        /*Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("isbn", book.getIsbn());
        params.put("title", book.getTitle());
        params.put("author", book.getAuthor());
        params.put("pages", book.getPages());
        params.put("cover", book.getCover().toString().toLowerCase());
        params.put("price", book.getPrice());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdated = jdbcTemplate.update(UPDATE_BOOK, source);
        if (rowsUpdated != 1) {
            throw new RuntimeException("Can't update book: " + book);
        }
        return getBookById(book.getId());*/
        //logger.debug("Database query UPDATE_BOOK");
        //logger.error("error in method - updateBook");
    }

    @Override
    public boolean deleteBook(Long id) {
        manager.getTransaction().begin();
       int row = manager.createNativeQuery(DELETE_BOOK).executeUpdate();
        manager.getTransaction().commit();
        manager.clear();
        return row == 1;
        /*int result = jdbcTemplate.update(DELETE_BOOK, Map.of("id", id));
        if (result != 1) {
            throw new RuntimeException("Can't delete book with id: " + id);
        }
        return true;*/
        //logger.debug("Database query DELETE_BOOK");
        //logger.error("error in method - deleteBook");
    }

    public int countAllBooks() {
        manager.getTransaction().begin();
        int rowQv = manager.createNativeQuery(COUNT_BOOK).executeUpdate();
        manager.getTransaction().commit();
        manager.clear();
        return rowQv;
    }
        /*try {
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
    }*/
   /*private static final Logger logger = LogManager.getLogger(BookDaoJdbcImpl.class);

    public static final String GET_ALL = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.deleted = false ORDER BY b.id";
    public static final String GET_BOOK_BY_ID = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.id = ? AND b.deleted = false ";
    public static final String GET_BOOK_BY_ISBN = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.isbn = ? AND b.deleted = false ";
    public static final String GET_BOOK_BY_AUTHOR = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.author = ? AND b.deleted = false ";
    public static final String CREATE_BOOK = "INSERT INTO books (isbn, title, author, pages, cover_id, price)  VALUES ( ?, ?, ?, ?, (SELECT id FROM covers WHERE name = ?) ,?)";
    public static final String UPDATE_BOOK = "UPDATE books SET isbn = ?, title = ?, author = ?, pages = ?, cover_id = (SELECT id FROM covers WHERE name = ?), price = ?  WHERE id = ? AND deleted = false";
    public static final String DELETE_BOOK = "UPDATE books SET deleted = true WHERE id = ? AND deleted = false";
    public static final String COUNT_BOOK = "SELECT COUNT(*) AS count FROM books WHERE deleted = false";

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
    }*/
}
