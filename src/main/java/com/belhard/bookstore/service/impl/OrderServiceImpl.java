package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.entity.Order;
import com.belhard.bookstore.dao.entity.OrderItem;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import com.belhard.bookstore.service.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final UserService userService;
    private final BookService bookService;

    public OrderServiceImpl(OrderDao orderDao, OrderItemDao orderItemDao, UserService userService, BookService bookService) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderDao.getAllOrders().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order entity = orderDao.getOrderById(id);
        return mapToDto(entity);
    }

    private OrderDto mapToDto(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setTotalCost(entity.getTotalCost());
        UserDto user = userService.getUserById(entity.getUserId());
        dto.setUser(user);
        List<OrderItemDto> itemDtos = new ArrayList<>();
        List<OrderItem> items = orderItemDao.getByOrderItemId(entity.getId());
        for (OrderItem item : items) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setId(item.getId());
            itemDto.setPrice(item.getPrice());
            itemDto.setQuantity(item.getQuantity());
            BookDto bookDto = bookService.getBookById(item.getBook_id());
            itemDto.setBook(bookDto);
            itemDtos.add(itemDto);
        }
        dto.setItems(itemDtos);
        return dto;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        BigDecimal totalCost = calculateOrderCost(orderDto);
        orderDto.setTotalCost(totalCost);

        Order entity = new Order();
        entity.setId(orderDto.getId());
        entity.setTotalCost(orderDto.getTotalCost());
        entity.setUserId(orderDto.getUser().getId());
        entity.setTimestamp(orderDto.getTimestamp());
        entity.setStatus(orderDto.getStatus());
        orderDao.updateOrder(entity);

        List<OrderItemDto> itemDtos = orderDto.getItems();
        for (OrderItemDto itemDto : itemDtos) {
            OrderItem item = mapToEntity(orderDto.getId(), itemDto);
            orderItemDao.createOrderItem(item);
        }
        return getOrderById(orderDto.getId());
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto) {
        BigDecimal totalCost = calculateOrderCost(orderDto);
        orderDto.setTotalCost(totalCost);

        Order entity = new Order();
        entity.setId(orderDto.getId());
        entity.setTotalCost(orderDto.getTotalCost());
        entity.setUserId(orderDto.getUser().getId());
        entity.setTimestamp(orderDto.getTimestamp());
        entity.setStatus(orderDto.getStatus());
        orderDao.updateOrder(entity);

        List<OrderItem> items = orderItemDao.getByOrderItemId(orderDto.getId());
        for (OrderItem item : items) {
            orderItemDao.getOrderItemById(item.getId());
        }

        List<OrderItemDto> itemDtos = orderDto.getItems();
        for (OrderItemDto itemDto : itemDtos) {
            OrderItem item = mapToEntity(orderDto.getId(), itemDto);
            orderItemDao.createOrderItem(item);
        }
        return getOrderById(orderDto.getId());

    }

    private BigDecimal calculateOrderCost(OrderDto orderDto) {
        List<OrderItemDto> items = orderDto.getItems();
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItemDto item : items) {
            BigDecimal itemCost = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
    }

    private OrderItem mapToEntity(Long orderId, OrderItemDto itemDto) {
        OrderItem item = new OrderItem();
        item.setOrder_id(orderId);
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
        item.setBook_id(itemDto.getBook().getId());
        return item;
    }

    @Override
    public void deleteOrder(Long id) {
        List<OrderItem> items = orderItemDao.getByOrderItemId(id);
        items.forEach(i -> {
            orderItemDao.deleteOrderItem(i.getId());
        });
    }
}
