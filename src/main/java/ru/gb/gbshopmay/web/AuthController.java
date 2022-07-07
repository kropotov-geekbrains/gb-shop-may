package ru.gb.gbshopmay.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.gbapimay.security.UserDto;
import ru.gb.gbshopmay.dao.security.AccountUserDao;
import ru.gb.gbshopmay.dao.security.ConfirmationCodeDao;
import ru.gb.gbshopmay.entity.security.AccountUser;
import ru.gb.gbshopmay.entity.security.ConfirmationCode;
import ru.gb.gbshopmay.service.UserService;
import ru.gb.gbshopmay.web.dto.mapper.UserMapper;

import javax.validation.Valid;
import java.util.Optional;

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
    private final ConfirmationCodeDao confirmationCodeDao;
    private static UserDto thisUser;

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
        } catch (UsernameNotFoundException ignored) {}
        thisUser = userService.register(userDto);
//        UserDto thisUser = userService.register(userDto);
        model.addAttribute("username", username);
        String confirmationCode = userService.getConfirmationCode();
        System.out.println("Confirmation code! " + confirmationCode);
        userService.generateConfirmationCode(thisUser, confirmationCode);
        model.addAttribute("id", thisUser.getId());
        System.out.println(thisUser.getId());// todo
        return "auth/registration-confirmation";
    }

    @PostMapping("/confirmation")
    public String handleConfirmationForm (String code, Model model) {
        model.addAttribute("code", code);
        System.out.println("code: " + code);
        ConfirmationCode confirmationCodeBy_id = confirmationCodeDao.findConfirmationCodeByAccountUser_Id(thisUser.getId());
        System.out.println(confirmationCodeBy_id.getCode() + " + from model: " + code);
        System.out.println("from model: " + code);
        if (confirmationCodeBy_id.equals(code)) {
            System.out.println(confirmationCodeBy_id.equals(code));
            AccountUser accountUser = confirmationCodeBy_id.getAccountUser();
            accountUser.setEnabled(true);
            accountUser.setAccountNonLocked(true);
            accountUser.setCredentialsNonExpired(true);
            accountUser.setAccountNonExpired(true);
            userService.update(accountUser);
            return "redirect:/auth/login";
        }
        System.out.println(!code.equals(confirmationCodeBy_id.toString()));
        return "auth/registration-confirmation";
    }

}
