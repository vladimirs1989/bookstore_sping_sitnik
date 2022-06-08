package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks(int page, int size);

    BookDto getBookById(Long id);

    BookDto getBookByIsbn(String isbn);

    List<BookDto> getBooksByAuthor(String author);

    BookDto createBook (BookDto bookDto);

    BookDto updateBook (BookDto bookDto);

    void deleteBook (Long id);

    int countAllBooks();

}
