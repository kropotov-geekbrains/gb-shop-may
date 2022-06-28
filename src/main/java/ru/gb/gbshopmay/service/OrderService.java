package ru.gb.gbshopmay.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.gbshopmay.entity.Order;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class OrderService {

    public static final String ORDER_ATTRIBUTE ="order";

    private final ProductService productService;

    public Order getCurrentOrder(HttpSession session) {
        Order order = (Order) session.getAttribute(ORDER_ATTRIBUTE);
        if (order == null) {
            order = new Order();
            session.setAttribute(ORDER_ATTRIBUTE, order);
        }
        return order;
    }

   /* public void addToOrder(HttpSession session, Long productId) {
        productService.findProductById(productId)
                .ifPresent((p) -> getCurrentOrder(session).add(p));
    }

    public void removeFromOrder(HttpSession session, Long productId) {
        productService.findProductById(productId)
                .ifPresent((p) -> getCurrentOrder(session).remove(p));
    }*/

    public void resetOrder(HttpSession session) {
        session.removeAttribute(ORDER_ATTRIBUTE);
    }
}
