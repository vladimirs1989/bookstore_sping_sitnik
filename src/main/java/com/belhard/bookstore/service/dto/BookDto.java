package com.belhard.bookstore.service.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class BookDto {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private Integer pages;
    private CoverDto cover;
    private BigDecimal price;

    public enum CoverDto{
        SOFT,
        HARD,
        ANOTHER
    }

    public BookDto() {
    }

    public BookDto(Long id, String isbn, String title, String author, Integer pages, CoverDto cover, BigDecimal price) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.cover = cover;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public CoverDto getCover() {
        return cover;
    }

    public void setCover(CoverDto cover) {
        this.cover = cover;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto book = (BookDto) o;
        return Objects.equals(id, book.id)
                && Objects.equals(isbn, book.isbn)
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && Objects.equals(pages, book.pages)
                && Objects.equals(cover, book.cover)
                && Objects.equals(price, book.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, author, pages, cover, price);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", cover='" + cover + '\'' +
                ", price=" + price +
                '}';
    }
}
