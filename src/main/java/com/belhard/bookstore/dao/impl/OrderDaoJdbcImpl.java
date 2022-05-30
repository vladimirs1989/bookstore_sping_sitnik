package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.entity.Order;
import com.belhard.bookstore.dao.mapper.OrderRowMapper;
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

@Repository("orderDao")
public class OrderDaoJdbcImpl implements OrderDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderRowMapper rowMapper;

    @Autowired
    public OrderDaoJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate, OrderRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    private static final Logger logger = LogManager.getLogger(OrderDaoJdbcImpl.class);

    public static final String GET_ALL = "SELECT o.id, o.user_id, o.total_cost, o.timestamp, s.name AS status FROM orders o JOIN statuses s ON o.status_id = s.id";
    public static final String GET_ORDER_BY_ID = "SELECT o.id, o.user_id, o.total_cost, o.timestamp, s.name AS status FROM orders o JOIN statuses s ON o.status_id = s.id WHERE o.id = :id ";
    public static final String CREATE_ORDER = "INSERT INTO orders (user_id, total_cost, timestamp, status_id)  VALUES ( :user_id, :total_cost, :timestamp,  (SELECT id FROM statuses WHERE name = :status))";
    public static final String UPDATE_ORDER = "UPDATE orders SET user_id = :user_id, total_cost = :total_cost, timestamp = :timestamp, status_id = (SELECT id FROM statuses WHERE name = :status)  WHERE id = :id ";
    public static final String DELETE_ORDER = "UPDATE orders SET status = cancel WHERE id = :id ";


    public List<Order> getAllOrders() {
        return jdbcTemplate.query(GET_ALL, rowMapper);
    }

    public Order getOrderById(Long id) {
        return jdbcTemplate.queryForObject(GET_ORDER_BY_ID, Map.of("id", id), rowMapper);
    }

    @Override
    public Order createOrder(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", order.getUserId());
        params.put("total_cost", order.getTotalCost());
        params.put("timestamp", order.getTimestamp());
        params.put("status", order.getStatus().toString().toLowerCase());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdated = jdbcTemplate.update(CREATE_ORDER, source, keyHolder, new String[]{"id"});
        if (rowsUpdated != 1) {
            throw new RuntimeException("Can't create order: " + order);
        }
        Long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new RuntimeException("Can't create order: " + order));
        return getOrderById(id);
    }

    @Override
    public Order updateOrder(Order order) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", order.getUserId());
        params.put("total_cost", order.getTotalCost());
        params.put("timestamp", order.getTimestamp());
        params.put("status", order.getStatus().toString().toLowerCase());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdated = jdbcTemplate.update(UPDATE_ORDER, source);
        if (rowsUpdated != 1) {
            throw new RuntimeException("Can't update order: " + order);
        }
        return getOrderById(order.getId());
    }


    public boolean deleteOrder(Long id) {
        int result = jdbcTemplate.update(DELETE_ORDER, Map.of("id", id));
        if (result != 1) {
            throw new RuntimeException("Can't delete order with id: " + id);
        }
        return true;
    }


}
