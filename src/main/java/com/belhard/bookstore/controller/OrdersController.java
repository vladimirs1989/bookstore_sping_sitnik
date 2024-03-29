package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import com.belhard.bookstore.service.dto.UserDto;
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

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrdersController  {

    private final BookService bookService;
    private final UserService userService;
    private final OrderService orderService;

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
    public String execute(Model model,  @PageableDefault(sort = {"id"}, direction =  Sort.Direction.ASC) Pageable pageable) {
        List<OrderDto> orders = orderService.getAllOrders(pageable);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable);
        return "orders";
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Model model, @RequestParam Map<String, Object> params){
        List<OrderItemDto> itemDtos = new ArrayList<>();
       // for (OrderItemDto orderItem : itemDtos) {
            OrderItemDto orderItem = new OrderItemDto();
            BookDto bookDto = bookService.getBookById(Long.valueOf(params.get("book").toString()));
            orderItem.setBookDto(bookDto);
            orderItem.setPrice(bookDto.getPrice());
            orderItem.setQuantity(Integer.valueOf(params.get("quantity").toString()));
            itemDtos.add(orderItem);

        OrderDto orderDto = new OrderDto();
        UserDto userDto = userService.getUserById(Long.valueOf(params.get("user").toString()));
        orderDto.setUserDto(userDto) ;
        orderDto.setTotalCost( new BigDecimal("0")/*orderDto.getTotalCost()*/);
        orderDto.setTimestamp(LocalDateTime.now());
        orderDto.setStatusDto(OrderDto.StatusDto.NOT_PAID);
        orderDto.setItems(itemDtos);


        OrderDto created = orderService.createOrder(orderDto);
        model.addAttribute("order", created);
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("userDto", userDto);
        return "order";
    }


    @PostMapping("/{id}")
    @Transactional
    public String update(Model model, @PathVariable Long id, @RequestParam Map<String, Object> params){
        OrderDto orderDto = orderService.getOrderById(id);
        List<OrderItemDto> itemDtos = new ArrayList<>();
        // for (OrderItemDto orderItem : itemDtos) {
        OrderItemDto orderItem = new OrderItemDto();
        BookDto bookDto = bookService.getBookById(Long.valueOf(params.get("book").toString()));
        orderItem.setBookDto(bookDto);
        orderItem.setPrice(bookDto.getPrice());
        orderItem.setQuantity(2/*Integer.valueOf(params.get("quantity").toString())*/);

        itemDtos.add(orderItem);

        UserDto userDto = userService.getUserById(Long.valueOf(params.get("user").toString()));
        orderDto.setUserDto(userDto) ;
        orderDto.setTotalCost( new BigDecimal("0")/*orderDto.getTotalCost()*/);
        orderDto.setTimestamp(LocalDateTime.now());
        orderDto.setStatusDto(OrderDto.StatusDto.PAID);
        orderDto.setItems(itemDtos);

        OrderDto updated = orderService.updateOrder(orderDto);
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("order", updated);
        return "order";
    }

    @PostMapping("/delete/{id}")
    public  String delete (Model model, @PathVariable Long id){
        orderService.deleteOrder(id);
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