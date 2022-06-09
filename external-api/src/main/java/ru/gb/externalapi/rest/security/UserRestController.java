package ru.gb.externalapi.rest.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.externalapi.entity.security.AccountUser;
import ru.gb.externalapi.service.UserService;
import ru.gb.gbapimay.security.UserDto;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUserList() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long id) {
        UserDto userDto;
        if (id != null) {
            userDto = userService.findById(id);
            if (userDto != null) {
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody UserDto userDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            UserDto savedUserDto = userService.register(userDto);
            httpHeaders.setLocation(URI.create("/api/v1/user/" + savedUserDto.getId()));
            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> body = new HashMap<>();
            body.put("apiError", e.getClass().getSimpleName());
            return new ResponseEntity<>(body, httpHeaders, HttpStatus.BAD_REQUEST);
        }



    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("userId") Long id, @Validated @RequestBody UserDto userDto) {
        userDto.setId(id);
        UserDto savedUserDto = userService.update(userDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/user/" + savedUserDto.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("userId") Long id) {
        userService.deleteById(id);
    }
}
