package com.belhard.bookstore.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.MERGE})
    private List<OrderItem> orderItems;

    public enum Status {
        CANCEL,
        PAID,
        NOT_PAID
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(totalCost, order.totalCost) && Objects.equals(timestamp, order.timestamp) && status == order.status && Objects.equals(user, order.user) && Objects.equals(orderItems, order.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalCost, timestamp, status, user, orderItems);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalCost=" + totalCost +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", user=" + user +
                ", orderItems=" + orderItems +
                '}';
    }
}
