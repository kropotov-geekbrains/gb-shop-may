package ru.gb.gbshopmay.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.gbshopmay.dao.OrderDao;
import ru.gb.gbshopmay.entity.Order;
import ru.gb.gbshopmay.web.dto.mapper.OrderMapper;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;

    private final OrderMapper orderMapper;

    public static final String ORDER_ATTRIBUTE ="order";

    public Order getCurrentOrder(HttpSession session) {
        Order order = (Order) session.getAttribute(ORDER_ATTRIBUTE);
        if (order == null) {
            order = new Order();
            session.setAttribute(ORDER_ATTRIBUTE, order);
        }
        return order;
    }

    public List<Order> findAll() {
        return orderDao.findAll();
    }

    public boolean save(Order order) {
        orderDao.save(order);
        return true;
    }

    public void resetOrder(HttpSession session) {
        session.removeAttribute(ORDER_ATTRIBUTE);
    }
}