package com.belhard.bookstore.dao.repository;

import com.belhard.bookstore.dao.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    //@Query("from OrderItem where order_id = :id")
    List<OrderItem> findByOrderId(/*@Param("id") */Long id);

}
