package ru.gb.gbshopmay.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapimay.product.dto.ProductDto;
import ru.gb.gbshopmay.service.ProductImageService;
import ru.gb.gbshopmay.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getProductList() {
        return productService.findAll();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") Long id) {
        ProductDto productDto;
        if (id != null) {
            productDto = productService.findById(id);
            if (productDto != null) {
                return new ResponseEntity<>(productDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody ProductDto productDto, MultipartFile multipartFile) {
        if (productService.presenceCheckManufacturer(productDto.getManufacturer()) && productService.presenceCheckCategory(productDto.getCategories())){
            ProductDto savedProductDto = productService.save(productDto, multipartFile);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(URI.create("/api/v1/product/" + savedProductDto.getId()));
            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        }else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

//    @PostMapping
//    public ResponseEntity<?> handlePost(@Validated @RequestBody ProductDto productDto) {
//        ProductDto savedProductDto = productService.save(productDto);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(URI.create("/api/v1/product/" + savedProductDto.getId()));
//        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
//    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id, @Validated @RequestBody ProductDto productDto,MultipartFile multipartFile) {
        productDto.setId(id);
        ProductDto savedProductDto = productService.save(productDto, multipartFile);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/product/" + savedProductDto.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("productId") Long id) {
        productService.deleteById(id);
    }
}
