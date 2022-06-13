package com.belhard.bookstore.dao.repository;

import com.belhard.bookstore.dao.entity.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query("from Order where deleted = false")
    List<Order> findAllOrder(Pageable pageable);


    @Modifying
    @Query(value = "update orders o set deleted = true where o.id =:id and deleted = false", nativeQuery = true)
    void delOrder(@Param("id") Long id);
}
