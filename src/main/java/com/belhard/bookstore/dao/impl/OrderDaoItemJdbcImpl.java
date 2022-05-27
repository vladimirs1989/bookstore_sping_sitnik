package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.entity.OrderItem;
import com.belhard.bookstore.dao.mapper.OrderItemRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("orderItemDao")
public class OrderDaoItemJdbcImpl implements OrderItemDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderItemRowMapper rowMapper;

    @Autowired
    public OrderDaoItemJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate, OrderItemRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    private static final Logger logger = LogManager.getLogger(OrderDaoItemJdbcImpl.class);

    public static final String GET_ALL = "SELECT o.id, o.order_id, o.book_id, o.quantity, o.price FROM order_items o";
    public static final String GET_ORDER_ITEM_BY_ID = "SELECT o.id, o.order_id, o.book_id, o.quantity, b.price FROM order_items o JOIN books b ON o.book_id = b.id WHERE o.id = :id ";
    public static final String CREATE_ORDER_ITEM = "INSERT INTO order_items (order_id, book_id, quantity, price)  VALUES ( :order_id, :book_id, :quantity, :price)";
    public static final String UPDATE_ORDER_ITEM = "UPDATE order_items SET order_id = :order_id, book_id = :book_id, quantity = :quantity, price = :price WHERE id = :id ";
    public static final String DELETE_ORDER_ITEM = "UPDATE order_items SET status = cancel WHERE id = :id ";


    public List<OrderItem> getAllOrderItems() {
        return jdbcTemplate.query(GET_ALL, rowMapper);
    }

    @Override
    public List<OrderItem> getByOrderItemId(Long id) {
        return null;
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return jdbcTemplate.queryForObject(GET_ORDER_ITEM_BY_ID, Map.of("id", id), rowMapper);
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("order_id", orderItem.getOrder_id());
        params.put("book_id", orderItem.getBook_id());
        params.put("quantity", orderItem.getQuantity());
        params.put("price", orderItem.getPrice());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdated = jdbcTemplate.update(CREATE_ORDER_ITEM, source, keyHolder, new String[]{"id"});
        if (rowsUpdated != 1) {
            throw new RuntimeException("Can't create order: " + orderItem);
        }
        Long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new RuntimeException("Can't create order: " + orderItem));
        return getOrderItemById(id);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        Map<String, Object> params = new HashMap<>();
        params.put("order_id", orderItem.getOrder_id());
        params.put("book_id", orderItem.getBook_id());
        params.put("quantity", orderItem.getQuantity());
        params.put("price", orderItem.getPrice());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdated = jdbcTemplate.update(UPDATE_ORDER_ITEM, source);
        if (rowsUpdated != 1) {
            throw new RuntimeException("Can't update order: " + orderItem);
        }
        return getOrderItemById(orderItem.getId());
    }

    @Override
    public boolean deleteOrderItem(Long id) {
        return false;
    }


    public boolean deleteOrder(Long id) {
        int result = jdbcTemplate.update(DELETE_ORDER_ITEM, Map.of("id", id));
        if (result != 1) {
            throw new RuntimeException("Can't delete order with id: " + id);
        }
        return true;
    }


}
