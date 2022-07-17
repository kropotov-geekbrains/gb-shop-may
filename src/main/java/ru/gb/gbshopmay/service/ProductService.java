package ru.gb.gbshopmay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.gb.gbapimay.common.enums.Status;
import ru.gb.gbapimay.product.dto.ProductDto;
import ru.gb.gbshopmay.config.JmsConfig;
import ru.gb.gbshopmay.dao.CategoryDao;
import ru.gb.gbshopmay.dao.ManufacturerDao;
import ru.gb.gbshopmay.dao.ProductDao;
import ru.gb.gbshopmay.entity.Category;
import ru.gb.gbshopmay.entity.Manufacturer;
import ru.gb.gbshopmay.entity.Product;
import ru.gb.gbshopmay.entity.ProductImage;
import ru.gb.gbshopmay.modelMessage.ChangePricedMessage;
import ru.gb.gbshopmay.web.dto.mapper.ProductMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductDao productDao;
    private final ProductMapper productMapper;
    private final ManufacturerDao manufacturerDao;
    private final CategoryDao categoryDao;
    private final JmsTemplate jmsTemplate;
    private final ProductImageService productImageService;

    public List<String> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) { //todo my for save
        return  Arrays.stream(files)
                .map(file -> productImageService.save(file))
                .collect(Collectors.toList());
    }

    public ProductDto save(ProductDto productDto, MultipartFile multipartFile) {
        Product product = productMapper.toProduct(productDto, manufacturerDao, categoryDao);
//        Product productFromDB = productDao.getById(productDto.getId()); // todo при создании нового продукта id=null
//        if (productFromDB != null && !productDto.getCost().equals(productFromDB.getCost())) sendMessage(productDto);
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

        return productMapper.toProductDto(productDao.save(product));
    }



    @Transactional
    public ProductDto save(final ProductDto productDto) {
        return save(productDto, null);
    }


//    @Scheduled(fixedRate = 1000)
    public void sendMessage(ProductDto productDto) {
        ChangePricedMessage message = ChangePricedMessage.builder()
                .message("The cost of the product: " + productDto.getTitle() + " has changed, the new price: " + productDto.getCost())
//                .message("The cost of the product ")
                .build();
        jmsTemplate.convertAndSend(JmsConfig.CHANGE_PRICE_PRODUCT_QUEUE, message);
    }

//    public void update(ProductDto productDto){
//        productDao.findById(productDto.getId()).ifPresent(
//                (p) -> product.setVersion(p.getVersion()));
//    }

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
