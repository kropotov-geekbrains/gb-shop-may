package ru.gb.gbshopmay.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.gbapimay.common.enums.OrderStatus;
import ru.gb.gbapimay.order.dto.OrderDto;
import ru.gb.gbshopmay.entity.Order;
import ru.gb.gbshopmay.entity.security.AccountUser;
import ru.gb.gbshopmay.service.CartService;
import ru.gb.gbshopmay.service.OrderService;
import ru.gb.gbshopmay.web.model.Cart;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final CartService cartService;

    private final OrderService orderService;

    @GetMapping
    public String getOrderList(Model model) {
        model.addAttribute("order", orderService.findAll());
        return "order/order-list";
    }

    @GetMapping("/fill")
    public String fill(Model model, HttpSession httpSession) {
        Cart cart = cartService.getCurrentCart(httpSession);
        model.addAttribute("cart", cart);
        model.addAttribute("orderDto", new OrderDto());
        return "order/order-form";
    }

    @PostMapping("/create")
    public String fillOrder(OrderDto orderDto, HttpSession httpSession) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = cartService.getCurrentCart(httpSession);
        Order order = Order
                .builder()
                .recipientFirstname(orderDto.getRecipientFirstname())
                .recipientLastname(orderDto.getRecipientLastname())
                .recipientPhone(orderDto.getRecipientPhone())
                .recipientMail(orderDto.getRecipientMail())
                .createdDate(LocalDateTime.now())
                .deliveryDate(orderDto.getDeliveryDate())
                .deliveryPrice(BigDecimal.ZERO)
                .status(OrderStatus.CREATED)
                .orderItems(cart.getItems())
                .accountUser((AccountUser) principal)
                .build();
        order.setPrice(cart.getTotalPrice());
        orderService.save(order);
        System.out.println(orderDto.getRecipientFirstname());
        return "order/create-order";
    }

}
