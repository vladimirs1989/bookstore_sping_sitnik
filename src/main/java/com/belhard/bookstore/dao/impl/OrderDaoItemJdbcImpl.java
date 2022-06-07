package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.entity.OrderItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("orderItemDao")
public class OrderDaoItemJdbcImpl implements OrderItemDao {

    @PersistenceContext
    private EntityManager manager;

    private static final Logger logger = LogManager.getLogger(OrderDaoItemJdbcImpl.class);

    public List<OrderItem> getAllOrderItems() {
        List<OrderItem> orderItems = manager.createQuery("from OrderItem ", OrderItem.class).getResultList();
        return orderItems;
    }

    @Override
    public List<OrderItem> getByOrderItemId(Long id) {
        List<OrderItem> orderItems = (List<OrderItem>)(manager.createQuery("from OrderItem where order_id =?1", OrderItem.class).setParameter(1, id).getResultList());
        return orderItems;
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        OrderItem orderItem = manager.find(OrderItem.class, id);
        return orderItem;
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        manager.persist(orderItem);
        return orderItem;
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        manager.merge(orderItem);
        return orderItem;
    }

    @Override
    public boolean deleteOrderItem(Long id) {
        int r = manager.createQuery("delete from OrderItem where id = ?1").setParameter(1, id).executeUpdate();
        if (r != 1) {
            throw new RuntimeException("Can't delete orderIem with id: " + id);
        }
        return true;
    }
}
