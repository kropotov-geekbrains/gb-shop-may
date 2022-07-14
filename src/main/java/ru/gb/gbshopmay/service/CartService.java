package ru.gb.gbshopmay.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.gbshopmay.dao.OrderItemDao;
import ru.gb.gbshopmay.entity.OrderItem;
import ru.gb.gbshopmay.web.model.Cart;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Artem Kropotov
 * created at 22.06.2022
 **/
@Service
@RequiredArgsConstructor
public class CartService {

    public static final String CART_ATTRIBUTE ="cart";
//    private final OrderItemDao orderItemDao;

    private final ProductService productService;

    public Cart getCurrentCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    public void addToCart(HttpSession session, Long productId) {
        productService.findProductById(productId)
                .ifPresent((p) -> getCurrentCart(session).add(p));
    }

//    public void saveOrderItem(HttpSession session, Long id) {
//        Cart currentCart = getCurrentCart(session);
//        List<OrderItem> items = currentCart.getItems();
//        for (OrderItem item : items) {
//            if (item.getProduct().getId() == id){
//                item.setQuantity((short)(item.getQuantity() + 1));
//                item.setTotalPrice(item.getProduct().getCost().multiply(new BigDecimal(item.getQuantity())));
//            }
//        }
//        OrderItem orderItem = items.get(0);
//        OrderItem orderItemFromDb  = OrderItem.builder().
//                quantity(orderItem.getQuantity())
//                .itemPrice(orderItem.getItemPrice())
//                .totalPrice(orderItem.getTotalPrice())
//                .product(orderItem.getProduct())
//                .order(null)
//                .build();
//        orderItemDao.save(orderItem);
//    }


    public void removeFromCart(HttpSession session, Long productId) {
        productService.findProductById(productId)
                .ifPresent((p) -> getCurrentCart(session).remove(p));
    }

    public BigDecimal getTotalPrice(HttpSession session) {
        return getCurrentCart(session).getTotalPrice();
    }

    public void resetCart(HttpSession session) {
        session.removeAttribute(CART_ATTRIBUTE);
    }


}
