package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.impl.BookServiceImpl;

import java.util.List;
import java.util.Scanner;

public class App {
    private static final BookService BOOK_SERVICE = new BookServiceImpl();
    public static final String HELP = "Reference: \n" +
            "all - see all book \n" +
            "get id - view selected book by Id \n" +
            "getByIsbn isbn - view selected book by Isbn \n" +
            "getByAuthor author - view selected book by author \n" +
            "create - create book \n" +
            "update - update book \n" +
            "count - number of books in the catalog \n" +
            "delete id - delete selected book \n" +
            "exit - exit\n" +
            "enter the command: ";

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        while (workWithBook(scanner)) {

        }
    }

    private static boolean workWithBook(Scanner scanner) {
        System.out.println(HELP);
        boolean programmIsActive = true;
        String key = scanner.next();
        switch (key) {
            case "all": {
                List<BookDto> books = BOOK_SERVICE.getAllBooks();
                books.forEach(System.out::println);
                break;
            }
            case "get": {
                String key1 = scanner.next();
                BookDto book = BOOK_SERVICE.getBookById(Long.valueOf(key1));
                System.out.println("User's book: ");
                System.out.println(book);
                break;
            }
            case "getByIsbn": {
                String isbn = scanner.next();
                BookDto book = BOOK_SERVICE.getBookByIsbn(isbn);
                System.out.println("User's book: ");
                System.out.println(book);
                break;
            }
            case "getByAuthor": {
                String author = scanner.next();
                List<BookDto> books = BOOK_SERVICE.getBooksByAuthor(author);
                books.forEach(System.out::println);
                break;
            }
            case "create": {
                BookDto book = new BookDto();
                System.out.println("");
                System.out.println("enter isbn");
                book.setIsbn(scanner.next());
                System.out.println("enter title");
                book.setTitle(scanner.next());
                System.out.println("enter author");
                book.setAuthor(scanner.next());
                System.out.println("enter pages");
                book.setPages(scanner.nextInt());
                System.out.println("enter cover");
                book.setCover(BookDto.CoverDto.valueOf(scanner.next()));
                System.out.println("enter price");
                book.setPrice(scanner.nextBigDecimal());
                book = BOOK_SERVICE.createBook(book);
                System.out.println(book);
                break;
            }
            case "update": {
                BookDto book = new BookDto();
                System.out.println("");
                System.out.println("enter new isbn");
                book.setIsbn(scanner.next());
                System.out.println("enter new title");
                book.setTitle(scanner.next());
                System.out.println("enter new author");
                book.setAuthor(scanner.next());
                System.out.println("enter new pages");
                book.setPages(scanner.nextInt());
                System.out.println("enter new cover");
                book.setCover(BookDto.CoverDto.valueOf(scanner.next()));
                System.out.println("enter new price");
                book.setPrice(scanner.nextBigDecimal());
                System.out.println("enter id");
                book.setId(scanner.nextLong());
                book = BOOK_SERVICE.updateBook(book);
                System.out.println(book);
                break;
            }
            case "delete": {
                String key1 = scanner.next();
                Long id = Long.valueOf(key1);
                BOOK_SERVICE.deleteBook(id);
                System.out.println("Books with id: " + id + " deleted successfully");
                break;
            }
            case "count":
                System.out.println("count of books " + BOOK_SERVICE.countAllBooks());
                break;
            case "exit":
                programmIsActive = false;
                break;
            default:
                System.out.println("command not find");
                break;
        }
        return programmIsActive;
    }

}
