package ru.geek.gbshopfront.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapimay.category.api.CategoryGateway;
import ru.gb.gbapimay.category.dto.CategoryDto;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryGateway categoryGateway;

    @GetMapping("/list")
    public String getCategoryList(Model model) {
        model.addAttribute("categories", categoryGateway.getCategoryList());
        return "category-list";
    }

//    @GetMapping("/{categoryId}")
//    public ResponseEntity<?> getManufacturer(@PathVariable("categoryId") Long id) {
//        return categoryGateway.getCategory(id);
//    }
//
//    @PostMapping
//    public ResponseEntity<?> handlePost(@Validated @RequestBody CategoryDto categoryDto) {
//        return categoryGateway.handlePost(categoryDto);
//    }
//
//    @PutMapping("/{categoryId}")
//    public ResponseEntity<?> handleUpdate(@PathVariable("categoryId") Long id, @Validated @RequestBody CategoryDto categoryDto) {
//        return categoryGateway.handleUpdate(id, categoryDto);
//
//    }
//
//    @DeleteMapping("/{categoryId}")
//    public void deleteById(@PathVariable("categoryId") Long id) {
//        categoryGateway.deleteById(id);
//    }
}
