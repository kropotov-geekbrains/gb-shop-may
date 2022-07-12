package ru.gb.gbshopmay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.OrderItem;

public interface OrderItemDao extends JpaRepository<OrderItem, Long> {
}
