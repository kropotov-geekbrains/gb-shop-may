package ru.gb.gbshopmay.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.gbapimay.product.dto.ProductDto;
import ru.gb.gbshopmay.dao.ProductDao;
import ru.gb.gbshopmay.dao.ProductImageDao;
import ru.gb.gbshopmay.service.CategoryService;
import ru.gb.gbshopmay.service.ManufacturerService;
import ru.gb.gbshopmay.service.ProductImageService;
import ru.gb.gbshopmay.service.ProductService;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;
    private final ProductImageService productImageService;
    private final ProductDao productDao;
    private final ProductImageDao productImageDao;


    @GetMapping("/all")
    public String getProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/product-list";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('product.create', 'product.update')")
    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        ProductDto productDto;
        if (id != null) {
            productDto = productService.findById(id);
//            List<String> images = new ArrayList<>(productImageService.uploadMultipleFilesByProductId(id));
//            model.addAttribute("productImages", images);
        } else {
            productDto = new ProductDto();
        }
        model.addAttribute("categoryService", categoryService);
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("product", productDto);
        return "product/product-form";
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('product.read')")
    public String showInfo(Model model, @PathVariable(name = "productId") Long id) {
        ProductDto productDto;
        if (id != null) {
            productDto = productService.findById(id);
        } else {
            return "redirect:/product/all";
        }
        List<Long> imagesId = new ArrayList<>(productImageService.uploadMultipleFiles(id));
        model.addAttribute("productImagesId", imagesId);
        model.addAttribute("product", productDto);
        model.addAttribute("categoryService", categoryService);
        return "product/product-info";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('product.create', 'product.update')")
//    public String saveProduct(ProductDto productDto) {
//        productDto.setManufactureDate(LocalDate.now()); // todo
//        productService.save(productDto);
    public String saveProduct(ProductDto product, @RequestParam("file") MultipartFile file, @RequestParam("files") MultipartFile[] files) {
        product.setManufactureDate(LocalDate.now());

        // todo
        productService.save(product, file);
//        productDao.findByTitle(product.getTitle()).get().getId();
        if (files.length == 0){
            System.out.println(files);
            uploadMultipleFiles(files, productDao.findByTitle(product.getTitle()).get().getId());
        }

        return "redirect:/product/all";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('product.delete')")
    public String deleteById(@PathVariable(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }
    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(productImageService.loadFileAsImage(id), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

     @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getAllImage(@PathVariable Long id) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(productImageService.loadFileAsImageByIdImage(id), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    public void uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, Long id) {
        Arrays.stream(files)
                .map(file -> productImageService.saveProductImage(id, file))
                .collect(Collectors.toList());
    }
}
