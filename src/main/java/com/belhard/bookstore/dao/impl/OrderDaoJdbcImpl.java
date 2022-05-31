package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("orderDao")
public class OrderDaoJdbcImpl implements OrderDao {

    @PersistenceContext
    private EntityManager manager;

    private static final Logger logger = LogManager.getLogger(OrderDaoJdbcImpl.class);

    public List<Order> getAllOrders() {
        List<Order> orders = manager.createQuery("from Order", Order.class).getResultList();
        manager.clear();
        return orders;
    }

    public Order getOrderById(Long id) {
        Order order = manager.find(Order.class, id);
        manager.clear();
        return order;
    }

    @Override
    public Order createOrder(Order order) {
        manager.persist(order);
        manager.clear();
        return order;
    }

    @Override
    public Order updateOrder(Order order) {
        manager.merge(order);
        return order;
    }

    public boolean deleteOrder(Long id) {
        int r = manager.createQuery("delete from Order where id = ?1").setParameter(1, id).executeUpdate();
        if (r != 1) {
            throw new RuntimeException("Can't delete order with id: " + id);
        }
        return true;
    }
}
