package ru.gb.gbshopmay.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.gbshopmay.entity.Product;
import ru.gb.gbshopmay.entity.Review;
import ru.gb.gbshopmay.entity.security.AccountUser;
import ru.gb.gbshopmay.service.CaptchaService;
import ru.gb.gbshopmay.service.ProductService;
import ru.gb.gbshopmay.service.ReviewService;
import ru.gb.gbshopmay.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final UserService userService;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final CaptchaService captchaService;
    private final ProductController productController;

    @GetMapping
    public String showReviewPage(Model model, HttpSession httpSession) {
        Review review = reviewService.getCurrentReview(httpSession);
        model.addAttribute("review", review);
        return "review/review-page";
    }

    @PostMapping
    public String saveReview(@RequestParam(name = "comment") String comment, @RequestParam(name = "id") Long id, Principal principal, HttpServletRequest request, Model model) {
        AccountUser accountUser = userService.findByUsername(principal.getName());
        Optional<Product> product = productService.findProductById(id);
        String captchaResponse = request.getParameter("g-recaptcha-response");
        String ip = request.getRemoteAddr();
        String recaptchaCheck = captchaService.verifyRecaptcha(ip, captchaResponse);
        ArrayList<String> errors = new ArrayList<>(captchaService.reviewValid(comment));
        System.out.println("check " + recaptchaCheck);
        if (!Objects.equals(recaptchaCheck, "")) {
            errors.add("Captcha not verified");
        }
        if (errors.size() == 0 && product.isPresent()) {
            Review review = Review.builder()
                    .approved(false)
                    .accountUser(accountUser)
                    .comment(comment)
                    .product(product.get())
                    .build();
            reviewService.save(review);
        }
        return productController.showInfo(model, id, errors);
    }
}