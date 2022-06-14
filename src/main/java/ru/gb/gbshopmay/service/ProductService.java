package ru.gb.gbshopmay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.gb.gbapimay.manufacturer.dto.ManufacturerDto;
import ru.gb.gbapimay.product.dto.ProductDto;
import ru.gb.gbshopmay.dao.CategoryDao;
import ru.gb.gbshopmay.dao.ManufacturerDao;
import ru.gb.gbshopmay.dao.ProductDao;
import ru.gb.gbshopmay.entity.Category;
import ru.gb.gbshopmay.entity.Manufacturer;
import ru.gb.gbshopmay.entity.Product;
import ru.gb.gbshopmay.entity.enums.Status;
import ru.gb.gbshopmay.web.dto.mapper.ProductMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductDao productDao;
    private final ProductMapper productMapper;
    private final ManufacturerDao manufacturerDao;
    private final CategoryDao categoryDao;


    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto, manufacturerDao, categoryDao);
        if (product.getId() != null) {
            productDao.findById(productDto.getId()).ifPresent(
                    (p) -> product.setVersion(p.getVersion())
            );
        }
        return productMapper.toProductDto(productDao.save(product));
    }


    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return productMapper.toProductDto(productDao.findById(id).orElse(null));
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

    public boolean presenceCheckManufacturer(String manufacturer) {
        Optional<Manufacturer> mayBeManufacturer = manufacturerDao.findByName(manufacturer);
        return mayBeManufacturer.isPresent();
    }


    public boolean presenceCheckCategory(Set<CategoryDto> categories) {
        List<Category> categoriesFromDb = new ArrayList<>();
        if(categories == null) {
            return false;
        } else
        for(CategoryDto category : categories) {
            Optional<Category> categoryFromDb = categoryDao.findById(category.getId());
            if (categoryFromDb.isPresent()) {
                categoriesFromDb.add(categoryFromDb.get());
            }
        }
            return categoriesFromDb.size() != 0;
        }
}
