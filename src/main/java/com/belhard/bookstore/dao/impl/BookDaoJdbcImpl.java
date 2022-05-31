package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("bookDao")
public class BookDaoJdbcImpl implements BookDao {

    @PersistenceContext
    private EntityManager manager;

    private static final Logger logger = LogManager.getLogger(BookDaoJdbcImpl.class);

    public static final String COUNT_BOOK = "SELECT COUNT(*) AS count FROM books WHERE deleted = false";

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = manager.createQuery("from Book", Book.class).getResultList();
        manager.clear();
        return books;
        //logger.debug("Database query GET_ALL");
        //logger.error("error in method - getAllBook");
    }

    @Override
    public Book getBookById(Long id) {
        Book book = manager.find(Book.class, id);
        manager.clear();
        return book;
        //logger.debug("Database query GET_BOOK_BY_ID");
        //logger.error("error in method - getBookById");
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        Book book = manager.find(Book.class, isbn);
        manager.clear();
        return book;
        //logger.debug("Database query GET_BOOK_BY_ISBN");
        //logger.error("error in method - getBookByIsbn");
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> books = manager.createQuery("from Book where author = ?1").setParameter(1, author).getResultList();
        manager.clear();
        return books;
        //logger.debug("Database query GET_BOOK_BY_AUTHOR");
        //logger.error("error in method - getBooksByAuthor");
    }

    @Override
    public Book createBook(Book book) {
        manager.persist(book);
        manager.clear();
        return book;
        //logger.debug("Database query CREATE_BOOK");
        //logger.error("error in method - createBook");
    }

    @Override
    public Book updateBook(Book book) {
        manager.merge(book);
        return book;
        //logger.debug("Database query UPDATE_BOOK");
        //logger.error("error in method - updateBook");
    }

    @Override
    public boolean deleteBook(Long id) {
        int r = manager.createQuery("delete from Book where id = ?1").setParameter(1, id).executeUpdate();
        if (r != 1) {
            throw new RuntimeException("Can't delete book with id: " + id);
        }
        return true;
        //logger.debug("Database query DELETE_BOOK");
        //logger.error("error in method - deleteBook");
    }

    public int countAllBooks() {
        int rowQv = manager.createNativeQuery(COUNT_BOOK).executeUpdate();
        manager.clear();
        return rowQv;
        //logger.debug("Database query COUNT_BOOK");
        //logger.error("error in method - countAllBooks"
    }
}
