package ru.gb.externalapi.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapimay.category.api.CategoryGateway;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.gb.gbapimay.product.dto.ProductDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryRestController {

    private final CategoryGateway categoryGateway;

    @GetMapping
    public List<CategoryDto> getCategoryList() {
        return categoryGateway.getCategoryList();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getManufacturer(@PathVariable("categoryId") Long id) {
        return categoryGateway.getCategory(id);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.handlePost(categoryDto);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("categoryId") Long id, @Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.handleUpdate(id, categoryDto);

    }

    @DeleteMapping("/{categoryId}")
    public void deleteById(@PathVariable("categoryId") Long id) {
        categoryGateway.deleteById(id);
    }
}
