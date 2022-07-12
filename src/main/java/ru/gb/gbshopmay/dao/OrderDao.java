package ru.gb.gbshopmay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.Order;

public interface OrderDao extends JpaRepository<Order, Long> {
}
