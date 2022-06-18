package ru.gb.thymeleafapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapimay.product.api.ProductGateway;
import ru.gb.gbapimay.product.dto.ProductDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")

public class ProductRestController {

    private final ProductGateway productGateway;


    @GetMapping
    public List<ProductDto> getProductList() {
        return productGateway.getProductList();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") Long id) {
        return productGateway.getProduct(id);
    }

    @PostMapping
    public ResponseEntity<ProductDto> handlePost(@Validated @RequestBody ProductDto productDto) {
        return productGateway.handlePost(productDto);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> handleUpdate(@PathVariable("productId") Long id, @Validated @RequestBody ProductDto productDto) {
        return productGateway.handleUpdate(id, productDto);

    }

    @DeleteMapping("/{productId}")
    public void deleteById(@PathVariable("productId") Long id) {
        productGateway.deleteById(id);
    }

}
