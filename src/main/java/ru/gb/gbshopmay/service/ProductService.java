package ru.gb.gbshopmay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.gbapimay.common.enums.Status;
import ru.gb.gbapimay.product.dto.ProductDto;
import ru.gb.gbshopmay.dao.CategoryDao;
import ru.gb.gbshopmay.dao.ManufacturerDao;
import ru.gb.gbshopmay.dao.ProductDao;
import ru.gb.gbshopmay.entity.Product;
import ru.gb.gbshopmay.entity.ProductImage;
import ru.gb.gbshopmay.web.dto.mapper.ProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductDao productDao;
    private final ProductMapper productMapper;
    private final ManufacturerDao manufacturerDao;
    private final CategoryDao categoryDao;
    private final ProductImageService productImageService;

    public ProductDto save(ProductDto productDto, MultipartFile multipartFile) {
        Product product = productMapper.toProduct(productDto, manufacturerDao, categoryDao);
        if (product.getId() != null) {
            productDao.findById(productDto.getId()).ifPresent(
                    (p) -> product.setVersion(p.getVersion())
            );
        }

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String pathToSavedFile = productImageService.save(multipartFile);
            ProductImage productImage = ProductImage.builder()
                    .path(pathToSavedFile)
                    .product(product)
                    .build();
            product.addImage(productImage);
        }
//        final ProductDto savedProductDto = ;

        return productMapper.toProductDto(productDao.save(product));
    }

    public ProductDto saveProductImage(Long productId, MultipartFile multipartFile) {
        Product product = productDao.getById(productId);
        String pathToSavedFile = productImageService.save(multipartFile);
        ProductImage productImage = ProductImage.builder()
                .path(pathToSavedFile)
                .product(product)
                .build();
        product.addImage(productImage);
        return productMapper.toProductDto(productDao.save(product));
    }

    @Transactional
    public ProductDto save(final ProductDto productDto) {
        return save(productDto, null);
    }


    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return productMapper.toProductDto(productDao.findById(id).orElse(null));
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(Long id) {
        return productDao.findById(id);
    }

    public List<ProductDto> findAll() {
        return productDao.findAll().stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        try {
            productDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }

    public void disable(Long id) {
        Optional<Product> product = productDao.findById(id);
        product.ifPresent(p -> {
            p.setStatus(Status.DISABLED);
            productDao.save(p);
        });
    }

}
