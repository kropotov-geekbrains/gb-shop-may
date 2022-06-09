package ru.geek.gbshopfront.controller.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapimay.common.enums.Status;
import ru.gb.gbapimay.product.api.ProductGateway;
import ru.gb.gbapimay.product.dto.ProductDto;
import ru.gb.gbapimay.security.UserDto;
import ru.gb.gbapimay.security.UserGateway;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserGateway userGateway;
    private final ProductGateway productGateway;

    @GetMapping()
    public String showRegistrationForm(Model model) {

        UserDto user = new UserDto();
        model.addAttribute("userDto", user);
        return "registration-form";
    }

    @PostMapping("")
    public String register(@ModelAttribute UserDto userDto, Model model) {
//        model.addAttribute("user", user);
        System.out.println(userDto.getUsername());
        System.out.println(userDto.getFirstname());
        System.out.println(userDto.getLastname());
        System.out.println(userDto.getEmail());
        System.out.println(userDto.getPhone());
        System.out.println(userDto.getPassword());
//        ProductDto productDto = ProductDto.builder().cost(BigDecimal.valueOf(11)).status(Status.ACTIVE).title("qwe").build();
//        productGateway.handlePost(productDto);
        ResponseEntity responseEntity = userGateway.handlePost(userDto);
        System.out.println(responseEntity.getBody());
        model.addAttribute("message", "user created successfully");
        return "registration-result";
    }



}
