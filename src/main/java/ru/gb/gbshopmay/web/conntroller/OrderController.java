package ru.gb.gbshopmay.web.conntroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.gbshopmay.entity.Order;
import ru.gb.gbshopmay.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String showOrderPage(Model model, HttpSession httpSession) {
        Order order = orderService.getCurrentOrder(httpSession);
        model.addAttribute("order", order);
        return "order/order-list";
    }

  /*  @GetMapping("/add/{id}")
    public String addProductToOrder(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        orderService.addToOrder(httpServletRequest.getSession(), id);
        String referer = httpServletRequest.getHeader("referer");
        return "redirect:" + referer;
    }

    @GetMapping("/delete/{id}")
    public String deleteProductFromOrder(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        orderService.removeFromOrder(httpServletRequest.getSession(), id);
        String referer = httpServletRequest.getHeader("referer");
        return "redirect:" + referer;
    }*/

    @GetMapping("/reset")
    public String resetOrder(HttpServletRequest httpServletRequest) {
        orderService.resetOrder(httpServletRequest.getSession());
        String referer = httpServletRequest.getHeader("referer");
        return "redirect:" + referer;
    }


}
