package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.entity.Book;
import com.belhard.bookstore.dao.impl.BookDaoJdbcImpl;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao = new BookDaoJdbcImpl();
    private static final Logger logger = LogManager.getLogger(BookServiceImpl.class);

    @Override
    public List<BookDto> getAllBooks() {
        logger.debug("Start method service - getAllBooks");
        List<Book> books = bookDao.getAllBooks();
        return books.stream().map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }

    private BookDto toDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setIsbn(entity.getIsbn());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setPages(entity.getPages());
        dto.setCover(BookDto.CoverDto.valueOf(entity.getCover().toString()));
        dto.setPrice(entity.getPrice());
        return dto;
    }

    @Override
    public BookDto getBookById(Long id) {
        logger.debug("Start method service - getBookById");
        Book book = bookDao.getBookById(id);
        if (book == null) {
            throw new RuntimeException("not Id");
        }
        return toDto(book);
    }

    @Override
    public BookDto getBookByIsbn(String isbn) {
        logger.debug("Start method service - getBookByIsbn");
        Book book = bookDao.getBookByIsbn(isbn);
        if (book == null) {
            throw new RuntimeException("not Isbn");
        }
        return toDto(book);
    }

    @Override
    public List<BookDto> getBooksByAuthor(String author) {
        logger.debug("Start method service - getBooksByAuthor");
        List<Book> books = bookDao.getBooksByAuthor(author);
        return books.stream().map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        logger.debug("Start method service - createBook");
        Book existing = bookDao.getBookByIsbn(bookDto.getIsbn());
        if(existing != null) {
            throw new RuntimeException("Book with Isbn exists");
        }
        Book newBook = toBook(bookDto);
        Book createdBook = bookDao.createBook(newBook);
        BookDto createdBookDto = toDto(createdBook);
        return createdBookDto;
    }

    private Book toBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPages(bookDto.getPages());
        book.setCover(Book.Cover.valueOf(bookDto.getCover().toString()));
        book.setPrice(bookDto.getPrice());
        return book;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        logger.debug("Start method service - updateBook");
        Book existing = bookDao.getBookByIsbn(bookDto.getIsbn());
        if(existing != null && existing.getId() != bookDto.getId()) {
            throw new RuntimeException("Book with Isbn exists");
        }
        Book newBook = toBook(bookDto);
        Book receivedBook = bookDao.updateBook(newBook);
        BookDto updatedBookDto = toDto(receivedBook);
        return updatedBookDto;
    }

    @Override
    public void deleteBook(Long id) {
        logger.debug("Start method service - deleteBook");
       if (!bookDao.deleteBook(id)){
           throw new RuntimeException("Book didn't delete");
       }
    }

    @Override
    public int countAllBooks() {
        logger.debug("Start method service - countAllBooks");
        return bookDao.countAllBooks();
    }
}
