package ru.gb.gbshopmay.web.conntroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.gbapimay.product.dto.ProductDto;
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
    private final ProductImageService productImageService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;


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
            List<String> images = new ArrayList<>(productImageService.uploadMultipleFilesByProductId(id));
            model.addAttribute("productImages", images);
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
        List<String> images = new ArrayList<>(productImageService.uploadMultipleFilesByProductId(id));
        model.addAttribute("productImages", images);
        model.addAttribute("product", productDto);
        return "product/product-info";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('product.create', 'product.update')")
    public String saveProduct(ProductDto product, @RequestParam("file") MultipartFile file) {
        product.setManufactureDate(LocalDate.now()); // todo
        productService.save(product, file);
        return "redirect:/product/all";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('product.delete')")
    public String deleteById(@PathVariable(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
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

    //  todo дз 11      Сделать загрузку множества изображений

//    @GetMapping(value = "/upload-multiple-files")
//    public void uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        Arrays.stream(files)
//                .map(file -> productImageService.save(file))
//                .collect(Collectors.toList());
//    }

    @PostMapping("/upload-multiple-files")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("id") Long id) {
        Arrays.stream(files)
                .map(file -> productService.saveProductImage(id, file))
                .collect(Collectors.toList());
        return "redirect:/product?id=" + id;
    }

    @GetMapping(value = "/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getImageByName(@PathVariable String name) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(productImageService.loadFileAsImageByFilename(name), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }
}
