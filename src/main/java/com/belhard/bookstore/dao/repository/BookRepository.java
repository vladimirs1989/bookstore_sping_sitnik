package com.belhard.bookstore.dao.repository;

import com.belhard.bookstore.dao.entity.Book;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("from Book where deleted = false")
    List<Book> findAllBook(PageRequest of);

    @Query("select b from Book b where b.isbn =?1 and deleted = false")
    Book findBookByIsbn(String isbn);

    @Query("select b from Book b where b.author =?1 and deleted = false")
    List<Book> findBookByAuthor(String author, PageRequest of);

    @Modifying
    @Query(value = "update books b set deleted = true where b.id =:id and deleted = false", nativeQuery = true)
    void deleteBook(@Param("id") Long id);
}
