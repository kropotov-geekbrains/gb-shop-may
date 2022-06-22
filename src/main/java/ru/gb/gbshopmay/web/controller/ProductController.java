package ru.gb.gbshopmay.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.gbapimay.product.api.ProductGateway;
import ru.gb.gbshopmay.service.ProductService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public String getManufacturerList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/product-list";
    }

//    @GetMapping("/{productId}")
//    public ResponseEntity<?> getManufacturer(@PathVariable("productId") Long id) {
//        return productGateway.getProduct(id);
//    }
//
//    @PostMapping
//    public ResponseEntity<?> handlePost(@Validated @RequestBody ProductDto productDto) {
//        return productGateway.handlePost(productDto);
//    }
//
//    @PutMapping("/{productId}")
//    public ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id, @Validated @RequestBody ProductDto productDto) {
//        return productGateway.handleUpdate(id, productDto);
//
//    }
//
//    @DeleteMapping("/{productId}")
//    public void deleteById(@PathVariable("productId") Long id) {
//        productGateway.deleteById(id);
//    }
}
