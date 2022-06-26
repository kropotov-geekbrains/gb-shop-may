package ru.gb.gbshopmay.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.gbapimay.security.UserDto;
import ru.gb.gbshopmay.dao.security.ConfirmationTokenDao;
import ru.gb.gbshopmay.entity.security.AccountUser;
import ru.gb.gbshopmay.entity.token.ConfirmationToken;
import ru.gb.gbshopmay.security.JpaUserDetailService;
import ru.gb.gbshopmay.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * @author Artem Kropotov
 * created at 19.06.2022
 **/
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JpaUserDetailService jpaUserDetailService;

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
            return "auth/registration-form";
        } catch (UsernameNotFoundException ignored) {
        }
        UserDto registeredUser = userService.register(userDto);
        model.addAttribute("username", username);

        String token = UUID.randomUUID().toString();
        System.out.println("Confirmation code: " + token);
        userService.generateConfirmationToken(registeredUser, token);
        model.addAttribute("token", token);
        return "auth/registration-confirmation";
    }

    @GetMapping("/auth/confirmRegistration")
    public String confirmRegistration(@RequestParam("token") String token, Model model) {
        ConfirmationToken confirmationToken = userService.getConfirmationToken(token);
        System.out.println("Confirmation code: " + confirmationToken.getConfirmationToken());
        AccountUser accountUser = confirmationToken.getAccountUser();
        accountUser.setEnabled(true);
        accountUser.setAccountNonLocked(true);
        accountUser.setCredentialsNonExpired(true);
        accountUser.setAccountNonExpired(true);
        userService.update(accountUser);
        return "auth/login-form";
    }
    // todo дз 9 добавить метод обработки кода подтверждения
}
