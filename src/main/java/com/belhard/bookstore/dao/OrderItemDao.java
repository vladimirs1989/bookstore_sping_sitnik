package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.entity.Order;
import com.belhard.bookstore.dao.entity.OrderItem;

import java.util.List;

public interface OrderItemDao {

    List<OrderItem> getAllOrderItems();

    List<OrderItem> getByOrderItemId(Long id);
    OrderItem getOrderItemById(Long id);

    OrderItem createOrderItem(OrderItem orderItem);

    OrderItem updateOrderItem(OrderItem orderItem);

    boolean deleteOrderItem(Long id);
}
