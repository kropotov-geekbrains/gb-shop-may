package ru.gb.gbshopmay.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.gbapimay.security.UserDto;
import ru.gb.gbshopmay.entity.VerificationToken;
import ru.gb.gbshopmay.entity.security.AccountUser;
import ru.gb.gbshopmay.service.UserService;
import ru.gb.gbshopmay.web.dto.mapper.UserMapper;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;



    @GetMapping("/login")
    public String loginPage() {
        return "auth/login-form";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "auth/registration-form";
    }

    @PostMapping("/register")
    public String handleRegistrationForm(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/registration-form";
        }

        final String username = userDto.getUsername();
        try {
            userService.findByUsername(username);
            model.addAttribute("userDto", userDto);
            model.addAttribute("registrationError", "Username already exists");

//            String appUrl = request.getContextPath();
//            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
//                    request.getLocale(), appUrl));
            return "auth/registration-form";
        } catch (UsernameNotFoundException ignored) {}
        UserDto user = userService.register(userDto);
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        model.addAttribute("tokenUrl", "http://localhost:8080/auth/confirmToken?token=" + token);
        model.addAttribute("username", username);
        return "auth/registration-confirmation";
    }

    @GetMapping("/confirmToken")
    public String confirmToken(@RequestParam String token, Model model) {
        System.out.println("token received! " + token);
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = "Invalid token";
            model.addAttribute("message", message);
            return "auth/badToken";
        }

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String message = "Token expired";
            model.addAttribute("message", message);
            return "auth/badToken";
        }

        AccountUser user = verificationToken.getUser();

        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        userService.update(user);
        return "auth/login-form";
    }

}