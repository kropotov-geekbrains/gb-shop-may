package ru.gb.gbshopmay.web.conntroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.gbapimay.order.dto.OrderDto;
import ru.gb.gbshopmay.entity.security.AccountUser;
import ru.gb.gbshopmay.service.CartService;
import ru.gb.gbshopmay.service.OrderService;
import ru.gb.gbshopmay.service.UserService;
import ru.gb.gbshopmay.web.dto.mapper.OrderMapper;
import ru.gb.gbshopmay.web.model.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Artem Kropotov
 * created at 22.06.2022
 **/
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final CartService cartService;

    @GetMapping
    public String showOrderPage(Model model, HttpSession session) {
        OrderDto orderDto = new OrderDto();
        Cart cart = cartService.getCurrentCart(session);
//        model.addAttribute("orderItems", orderItemDao.findAll());
        model.addAttribute("orderDto", orderDto);
        model.addAttribute("cart", cart);
//        orderService.saveOrderItem();
        return "order/order-form";
    }

    @GetMapping("/fill")
    public String OrderFill(Model model, HttpSession session) {
        OrderDto orderDto = new OrderDto();
//        model.addAttribute("orderItems", orderItemDao.findAll());
        Cart cart = cartService.getCurrentCart(session);
        model.addAttribute("cart", cart);
        model.addAttribute("orderDto", orderDto);
        return "order/order-form";
    }

    @PostMapping("/checkout")
    public String OrderSave(@Valid OrderDto orderDto, Model model, HttpSession session) {
        model.addAttribute("orderDto", orderDto);
        Cart cart = cartService.getCurrentCart(session);
        AccountUser username = userService.findByUsername("admin");
        OrderDto saveOrderDto = orderService.save(orderDto, username, cart.getTotalPrice());
        orderService.saveOrderItemsFromCart(session, orderMapper.toOrder(saveOrderDto));
        return "order/order-track";
    }

    @GetMapping("/reset")
    public String resetOrder(HttpServletRequest httpServletRequest) {
        orderService.resetOrder(httpServletRequest.getSession());
        String referer = httpServletRequest.getHeader("referer");
        return "redirect:" + referer;
    }

    public AccountUser getCurrentUser(HttpSession session) {
        AccountUser thisUser = (AccountUser) session.getAttribute("thisUser");
        return thisUser;
    }


}
