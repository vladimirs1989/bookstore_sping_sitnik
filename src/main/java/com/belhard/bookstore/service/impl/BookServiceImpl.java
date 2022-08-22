package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.entity.Book;
import com.belhard.bookstore.dao.repository.BookRepository;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("bookService")
@Transactional

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private static final Logger logger = LogManager.getLogger(BookServiceImpl.class);

    @Override
    public List<BookDto> getAllBooks(Pageable pageable) {
        logger.debug("Start method service - getAllBooks");
        Iterable<Book> books = bookRepository.findAllBook(pageable);
        List<Book> bookList = new ArrayList<>();
        books.forEach(bookList::add);
        return bookList.stream().map(entity -> toDto(entity))
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
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new RuntimeException("No book with id: " + id);
        }
        return toDto(bookOptional.get());
    }

    @Override
    public BookDto getBookByIsbn(String isbn) {
        logger.debug("Start method service - getBookByIsbn");
        Book book = bookRepository.findBookByIsbn(isbn);
        if (book == null) {
            throw new RuntimeException("not Isbn");
        }
        return toDto(book);
    }

    @Override
    public List<BookDto> getBooksByAuthor(String author) {
        logger.debug("Start method service - getBooksByAuthor");
        List<Book> books = bookRepository.findBookByAuthor(author, PageRequest.of(0, 10, Sort.Direction.ASC,  "author","title"));
        return books.stream().map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        logger.debug("Start method service - createBook");
        /*Book existing = bookDao.getBookByIsbn(bookDto.getIsbn());
        if (existing != null) {
            throw new RuntimeException("Book with Isbn exists");
        }*/
        Book newBook = toBook(bookDto);
        Book createdBook = bookRepository.save(newBook);
        BookDto createdBookDto = toDto(createdBook);
        return createdBookDto;
    }

    public Book toBook(BookDto bookDto) {
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
        /*Book existing = bookDao.getBookByIsbn(bookDto.getIsbn());
        if (existing != null && existing.getId() != bookDto.getId()) {
            throw new RuntimeException("Book with Isbn exists");
        }*/
        Book newBook = toBook(bookDto);
        Book receivedBook = bookRepository.save(newBook);
        BookDto updatedBookDto = toDto(receivedBook);
        return updatedBookDto;
    }

    @Override
    public void deleteBook(Long id) {
        logger.debug("Start method service - deleteBook");
        bookRepository.deleteBook(id);
    }

    @Override
    public int countAllBooks() {
        logger.debug("Start method service - countAllBooks");
        return (int) bookRepository.count();
    }
}
