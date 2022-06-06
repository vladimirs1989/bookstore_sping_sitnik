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

    public static final String GET_BOOK_BY_ISBN = "select b from Book b where b.isbn =?1 and deleted = false";
    public static final String GET_BOOK_BY_AUTHOR = "select b from Book b where b.author =?1 and deleted = false";
    public static final String DELETE_BOOK = "update Book b set deleted = true where b.id =?1 and deleted = false";
    public static final String COUNT_BOOK = "select count(b) FROM books b where deleted = false";

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = manager.createQuery("from Book where deleted = false", Book.class).getResultList();
        return books;
        //logger.debug("Database query GET_ALL");
        //logger.error("error in method - getAllBook");
    }

    @Override
    public Book getBookById(Long id) {
        Book book = manager.find(Book.class, id);
        return book;
        //logger.debug("Database query GET_BOOK_BY_ID");
        //logger.error("error in method - getBookById");
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        Book book = (Book) manager.createQuery(GET_BOOK_BY_ISBN).setParameter(1,isbn).getSingleResult();
        //manager.clear();
        return book;
        //logger.debug("Database query GET_BOOK_BY_ISBN");
        //logger.error("error in method - getBookByIsbn");
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> books = manager.createQuery(GET_BOOK_BY_AUTHOR).setParameter(1,author).getResultList();
        //manager.clear();
        return books;
        //logger.debug("Database query GET_BOOK_BY_AUTHOR");
        //logger.error("error in method - getBooksByAuthor");
    }

    @Override
    public Book createBook(Book book) {
        manager.persist(book);
        //manager.clear();
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
        int r = manager.createQuery(DELETE_BOOK).setParameter(1, id).executeUpdate();
        if (r != 1) {
            throw new RuntimeException("Can't delete book with id: " + id);
        }
        return true;
        //logger.debug("Database query DELETE_BOOK");
        //logger.error("error in method - deleteBook");
    }

    public int countAllBooks() {
        int rowQv = manager.createQuery(COUNT_BOOK).executeUpdate();
        manager.clear();
        return rowQv;
        //logger.debug("Database query COUNT_BOOK");
        //logger.error("error in method - countAllBooks"
    }
}
