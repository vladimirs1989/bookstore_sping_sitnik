package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.dao.entity.Order;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import com.belhard.bookstore.service.dto.UserDto;
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

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrdersController  {

    private static BookService bookService;
    private static UserService userService;
    private static OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService, BookService bookService, UserService userService) {
        this.orderService = orderService;
        this.bookService = bookService;
        this.userService = userService;
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
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Model model, @RequestParam Map<String, Object> params){
        List<OrderItemDto> itemDtos = new ArrayList<>();
       // for (OrderItemDto orderItem : itemDtos) {
            OrderItemDto orderItem = new OrderItemDto();
            BookDto bookDto = bookService.getBookById(9L);
            orderItem.setBookDto(bookDto);
            orderItem.setPrice(bookDto.getPrice());
            orderItem.setQuantity(2/*Integer.valueOf(params.get("quantity").toString())*/);

            itemDtos.add(orderItem);

        OrderDto orderDto = new OrderDto();
        UserDto userDto = userService.getUserById(2L);
        orderDto.setUserDto(userDto) ;
        orderDto.setTotalCost( new BigDecimal("20")/*orderDto.getTotalCost()*/);
        orderDto.setTimestamp(LocalDateTime.now());
        orderDto.setStatusDto(OrderDto.StatusDto.NOT_PAID);
        orderDto.setItems(itemDtos);


        OrderDto created = orderService.createOrder(orderDto);
        model.addAttribute("order", created);
        return "order";
    }

    @PostMapping("/{id}")
    public String update(Model model, @PathVariable Long id, @RequestParam Map<String, Object> params){
        OrderDto orderDto = orderService.getOrderById(id);

//        bookDto.setIsbn(params.get("isbn").toString());
//        bookDto.setTitle(params.get("title").toString());
//        bookDto.setAuthor(params.get("author").toString());
//        bookDto.setPages(Integer.valueOf(params.get("pages").toString()));
//        bookDto.setCover(BookDto.CoverDto.valueOf(params.get("cover").toString()));
//        bookDto.setPrice( new BigDecimal (params.get("price").toString()));
        OrderDto updated = orderService.updateOrder(orderDto);
        model.addAttribute("order", updated);
        return "order";
    }

    @PostMapping("/delete/{id}")
    public  String delete (Model model, @PathVariable Long id){
        bookService.deleteBook(id);
        model.addAttribute("message", "Order with id = " + id + " is deleted");
        return "delete";
    }

    @GetMapping("/edit/{id}")
    public  String editForm (Model model, @PathVariable Long id){
        OrderDto orderDto = orderService.getOrderById(id);
        model.addAttribute("order", orderDto);
        return "orderUpdate";
    }

    @GetMapping("/create")
    public String createForm(){
        return "orderCreate";
    }
}