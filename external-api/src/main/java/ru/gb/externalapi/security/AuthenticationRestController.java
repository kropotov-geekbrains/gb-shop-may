package ru.gb.externalapi.security;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.externalapi.entity.security.AccountUser;
import ru.gb.externalapi.security.jwt.JwtTokenProvider;
import ru.gb.externalapi.service.UserService;
import ru.gb.gbapimay.security.AuthenticationUserDto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Artem Kropotov
 * created at 05.06.2022
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationUserDto authenticationUserDto) {
        try {
            final String username = authenticationUserDto.getUsername();
            AccountUser accountUser = userService.findByUsername(username);

            String token = jwtTokenProvider.createToken(username, accountUser.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
//            response.get(token).addHeader;

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
