package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book/{id}")
    public String execute(Model model, @PathVariable Long id) {

        BookDto bookDto = bookService.getBookById(id);
        //session.setAttribute("book", bookDto);
        if (bookDto == null) {
            model.addAttribute("message", "No book with id: " + id);
            return "error";
        }
        model.addAttribute("book", bookDto);
        return "book";
    }

    @GetMapping("/isbn/{isbn}")
    public String execute(Model model, @PathVariable String isbn) {
        BookDto bookDto = bookService.getBookByIsbn(isbn);
        if (bookDto == null) {
            model.addAttribute("message", "No book with isbn: " + isbn);
            return "error";
        }
        model.addAttribute("book", bookDto);
        return "book";
    }

    @GetMapping
    public String execute(Model model, @PageableDefault(sort = {"author"}, direction =  Sort.Direction.ASC) Pageable pageable) {
        List<BookDto> books = bookService.getAllBooks(pageable);
        model.addAttribute("books", books);
        model.addAttribute("page", pageable);


        return "books";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Model model, @RequestParam Map<String, Object> params){
        BookDto bookDto = new BookDto();
        bookDto.setIsbn(params.get("isbn").toString());
        if (bookDto.getIsbn() == null) {
            model.addAttribute("message", "No entered isbn ");
            return "error";
        }
        bookDto.setTitle(params.get("title").toString());
        bookDto.setAuthor(params.get("author").toString());
        bookDto.setPages(Integer.valueOf(params.get("pages").toString()));
        bookDto.setCover(BookDto.CoverDto.valueOf(params.get("cover").toString()));
        bookDto.setPrice( new BigDecimal (params.get("price").toString()));
        BookDto created = bookService.createBook(bookDto);
        model.addAttribute("book", created);
        return "book";
    }

    @PostMapping("/{id}")
    public String update(Model model, @PathVariable Long id, @RequestParam Map<String, Object> params){
        BookDto bookDto = bookService.getBookById(id);
        bookDto.setIsbn(params.get("isbn").toString());
        bookDto.setTitle(params.get("title").toString());
        bookDto.setAuthor(params.get("author").toString());
        bookDto.setPages(Integer.valueOf(params.get("pages").toString()));
        bookDto.setCover(BookDto.CoverDto.valueOf(params.get("cover").toString()));
        bookDto.setPrice( new BigDecimal (params.get("price").toString()));
        BookDto updated = bookService.updateBook(bookDto);
        model.addAttribute("book", updated);
        return "book";
    }

    @PostMapping("/delete/{id}")
    public  String delete (Model model, @PathVariable Long id){
        bookService.deleteBook(id);
        model.addAttribute("message", "Book with id = " + id + " is deleted");
        return "delete";
    }

    @GetMapping("/edit/{id}")
    public  String editForm (Model model, @PathVariable Long id){
        BookDto bookDto = bookService.getBookById(id);
        model.addAttribute("book", bookDto);
        return "bookUpdate";
    }

    @GetMapping("/create")
    public String createForm(){
        return "bookCreate";
    }
}