package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.dao.entity.Book;
import com.belhard.bookstore.dao.entity.OrderItem;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrdersController  {

    @Autowired
    private static OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/{id}")
    public String execute(Model model, @PathVariable Long id) {
        OrderDto orderDto = orderService.getOrderById(id);
        System.out.println(orderDto);
        if (orderDto == null) {
            model.addAttribute("message", "No order with id: " + id);
            return "error";
        }
        model.addAttribute("order", orderDto);
        return "order";
    }


    @GetMapping
    public String execute(Model model) {
        List<OrderDto> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Model model, @RequestParam Map<String, Object> params){

            OrderItem orderItem = new OrderItem();
            orderItem.setBook(orderItem.getBook());
            orderItem.setPrice(new BigDecimal(params.get("price").toString()));
            orderItem.setQuantity(Integer.valueOf(params.get("quantity").toString()));




        OrderDto orderDto = new OrderDto();
        orderDto.setUserDto(orderDto.getUserDto());
        orderDto.setTotalCost(orderDto.getTotalCost());
        orderDto.setTimestamp(LocalDateTime.now());
        orderDto.setStatusDto(OrderDto.StatusDto.NOT_PAID);

        OrderDto created = orderService.createOrder(orderDto);
        model.addAttribute("order", created);
        return "order";
    }

//    @PostMapping("/{id}")
//    public String update(Model model, @PathVariable Long id, @RequestParam Map<String, Object> params){
//        BookDto bookDto = bookService.getBookById(id);
//        bookDto.setIsbn(params.get("isbn").toString());
//        bookDto.setTitle(params.get("title").toString());
//        bookDto.setAuthor(params.get("author").toString());
//        bookDto.setPages(Integer.valueOf(params.get("pages").toString()));
//        bookDto.setCover(BookDto.CoverDto.valueOf(params.get("cover").toString()));
//        bookDto.setPrice( new BigDecimal (params.get("price").toString()));
//        BookDto updated = bookService.updateBook(bookDto);
//        model.addAttribute("book", updated);
//        return "book";
//    }
//
//    @PostMapping("/delete/{id}")
//    public  String delete (Model model, @PathVariable Long id){
//        bookService.deleteBook(id);
//        model.addAttribute("message", "Book with id = " + id + " is deleted");
//        return "delete";
//    }
//
//    @GetMapping("/edit/{id}")
//    public  String editForm (Model model, @PathVariable Long id){
//        BookDto bookDto = bookService.getBookById(id);
//        model.addAttribute("book", bookDto);
//        return "bookUpdate";
//    }

    @GetMapping("/create")
    public String createForm(){
        return "orderCreate";
    }
}