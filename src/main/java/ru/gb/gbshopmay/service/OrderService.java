package ru.gb.gbshopmay.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.gbapimay.common.enums.OrderStatus;
import ru.gb.gbapimay.order.dto.OrderDto;
import ru.gb.gbshopmay.dao.OrderDao;
import ru.gb.gbshopmay.dao.OrderItemDao;
import ru.gb.gbshopmay.entity.Order;
import ru.gb.gbshopmay.entity.OrderItem;
import ru.gb.gbshopmay.entity.security.AccountUser;
import ru.gb.gbshopmay.web.dto.mapper.OrderMapper;
import ru.gb.gbshopmay.web.model.Cart;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Artem Kropotov
 * created at 22.06.2022
 **/
@Service
@RequiredArgsConstructor
public class OrderService {

    public static final String ORDER_ATTRIBUTE ="order";
//    private final OrderStatus orderStatus;

    private final ProductService productService;
    private final OrderMapper orderMapper;
    private final OrderDao orderDao;
    private final CartService cartService;
    private final OrderItemDao orderItemDao;

    public void saveOrderItemsFromCart(HttpSession session, Order order) {
        Cart currentCart = cartService.getCurrentCart(session);
        List<OrderItem> items = currentCart.getItems();
        for (int i = 0; i < items.size(); i++) {
            saveOrderItem(items.get(i), order);
        }
    }

    public OrderDto save(OrderDto orderDto, AccountUser accountUser, BigDecimal price){
        Order order = orderMapper.toOrder(orderDto);
        order.setStatus(OrderStatus.CREATED);
        order.setDeliveryDate(LocalDate.now());
        order.setDeliveryPrice(new BigDecimal(120));
        order.setAccountUser(accountUser);
        order.setPrice(price);
        return orderMapper.toOrderDto(orderDao.save(order));
    }

    public void saveOrderItem (OrderItem orderItem, Order order){
        orderItem  = OrderItem.builder().
                quantity(orderItem.getQuantity())
                .itemPrice(orderItem.getItemPrice())
                .totalPrice(orderItem.getTotalPrice())
                .product(orderItem.getProduct())
                .order(order)
                .build();
        orderItemDao.save(orderItem);
    }


    public void resetOrder(HttpSession session) {
        session.removeAttribute(ORDER_ATTRIBUTE);
    }
}
