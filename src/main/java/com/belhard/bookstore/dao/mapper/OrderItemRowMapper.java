package com.belhard.bookstore.dao.mapper;

import com.belhard.bookstore.dao.entity.Order;
import com.belhard.bookstore.dao.entity.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        OrderItem order = new OrderItem();
        //order.setOrder_id(resultSet.getLong("order_id"));
        //order.setBook_id(resultSet.getLong("book_id"));
        order.setQuantity(resultSet.getInt("quantity"));
        order.setPrice(resultSet.getBigDecimal("price"));
        return order;
    }
}
