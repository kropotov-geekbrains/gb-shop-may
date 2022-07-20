package ru.gb.gbshopmay.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.gb.gbshopmay.service.CaptchaGenerator;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Controller
public class CaptchaController {

    private final CaptchaGenerator captchaGenerator;


//    @GetMapping(value = "/captcha", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getCaptcha(){
        Map<String, BufferedImage> captcha = captchaGenerator.generateCaptcha();// генерирует картинку с капчой со словом кторое содержится в строке
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
//            Collection<BufferedImage> bufferedImages = captcha.values();
//            captcha.get(0);
//            bufferedImages.
            Set<String> keySet = captcha.keySet();
            System.out.println(keySet + " + " + captcha.get(keySet));
            ImageIO.write(captcha.get(0), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};

        // captcha. key класть в сессиию
//        captcha
        // captcha.value класть в ImageIO
        // положить в сессию captchaString

//        return null;
//                ImageIO.write(captha.value, "png", new ByteArrayOutputStream());
    }
// тоже самое что в картинках к продуктам, только надо имя класть в сессию
    //  todo дз 11      Сделать загрузку множества изображений

//    ReviewDto {
//        String productId;
//        String comment;
//        String captchaCode;
//    }
//
    // в маппере наййти productById из БД


    // сделать вывод сообщения об ошибке и валидацию капчи через BindingResult
//    @PostMapping("/review")
//    public String addReview(ReviewDto reviewDto, HttpSession httpSession, Principal principal) {
//        userService.findByUsername(principal.getName());
//
//        reviewService.save(ReviewDto)
//    }
}