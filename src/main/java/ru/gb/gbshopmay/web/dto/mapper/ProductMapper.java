package ru.gb.gbshopmay.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.gbapimay.product.dto.ProductDto;
import ru.gb.gbshopmay.dao.ManufacturerDao;
import ru.gb.gbshopmay.entity.Manufacturer;
import ru.gb.gbshopmay.entity.Product;

import java.util.NoSuchElementException;

@Mapper(uses = ManufacturerMapper.class)
public interface ProductMapper {
    Product toProduct(ProductDto productDto, @Context ManufacturerDao manufacturerDao);

    ProductDto toProductDto(Product product);


    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        return manufacturerDao.findByName(manufacturer).orElseThrow(NoSuchElementException::new);
    }

    default String getManufacturer(Manufacturer manufacturer) {
        return manufacturer.getName();
    }
}
