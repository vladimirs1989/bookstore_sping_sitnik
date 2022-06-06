package com.belhard.bookstore.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderDto {

    private Long id;
    private UserDto userDto;
    private BigDecimal totalCost;
    private LocalDateTime timestamp;
    private StatusDto statusDto;
    private List<OrderItemDto> items;

    public enum StatusDto{
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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
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

    public StatusDto getStatusDto() {
        return statusDto;
    }

    public void setStatusDto(StatusDto statusDto) {
        this.statusDto = statusDto;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(id, orderDto.id) && Objects.equals(userDto, orderDto.userDto) && Objects.equals(totalCost, orderDto.totalCost) && Objects.equals(timestamp, orderDto.timestamp) && statusDto == orderDto.statusDto && Objects.equals(items, orderDto.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userDto, totalCost, timestamp, statusDto, items);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", user=" + userDto +
                ", totalCost=" + totalCost +
                ", timestamp=" + timestamp +
                ", status=" + statusDto +
                ", items=" + items +
                '}';
    }
}
