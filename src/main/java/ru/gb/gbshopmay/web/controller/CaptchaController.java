package ru.gb.gbshopmay.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class CaptchaController {

    @GetMapping(value = "/captcha", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getCaptcha (HttpSession httpSession) {

        return new byte[]{};
    }
}
