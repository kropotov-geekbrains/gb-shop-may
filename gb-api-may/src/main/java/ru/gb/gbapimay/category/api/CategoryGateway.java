package ru.gb.gbapimay.category.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapimay.category.dto.CategoryDto;

import java.util.List;
//@FeignClient(url = "http://localhost:8080/api/v1/category", name = "CategoryGateway")
public interface CategoryGateway {
    @GetMapping
    List<CategoryDto> getCategoryList();

    @GetMapping("/{categoryId}")
    ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Long id);

    @PostMapping
    ResponseEntity<CategoryDto> handlePost(@Validated @RequestBody CategoryDto categoryDto);

    @PutMapping("/{categoryId}")
    ResponseEntity<CategoryDto> handleUpdate(@PathVariable("categoryId") Long id,
                                             @Validated @RequestBody CategoryDto categoryDto);

    @DeleteMapping("/{categoryId}")
    void deleteById(@PathVariable("categoryId") Long id);

}
