package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrders(int page, int size);

    OrderDto getOrderById(Long id);

    OrderDto createOrder(OrderDto orderDto);

    OrderDto updateOrder(OrderDto orderDto);

    void deleteOrder(Long id);

}
