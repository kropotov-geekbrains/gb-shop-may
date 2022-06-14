package ru.gb.gbshopmay.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.gb.gbapimay.product.dto.ProductDto;
import ru.gb.gbshopmay.dao.CategoryDao;
import ru.gb.gbshopmay.dao.ManufacturerDao;
import ru.gb.gbshopmay.entity.Category;
import ru.gb.gbshopmay.entity.Manufacturer;
import ru.gb.gbshopmay.entity.Product;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = ManufacturerMapper.class)
public interface ProductMapper {
    Product toProduct(ProductDto productDto, @Context ManufacturerDao manufacturerDao, @Context CategoryDao categoryDao);

    ProductDto toProductDto(Product product);


    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        return manufacturerDao.findByName(manufacturer).orElseThrow(
                () -> new NoSuchElementException("There isn't manufacturer with name " + manufacturer)
        );
    }

    default String getManufacturer(Manufacturer manufacturer) {
        return manufacturer.getName();
    }

    default Set<Category> categoryDtoSetToCategorySet(Set<CategoryDto> categories, @Context CategoryDao categoryDao) {
        return categories.stream().map(c -> categoryDao.findById(c.getId())
                        .orElseThrow(
                                () -> new NoSuchElementException("There isn't category with id: " + c.getId())
                        ))
                .collect(Collectors.toSet());
    }
}
