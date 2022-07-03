package ru.gb.gbshopmay;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.gb.gbapimay.common.enums.Status;
import ru.gb.gbshopmay.dao.CategoryDao;
import ru.gb.gbshopmay.dao.ManufacturerDao;
import ru.gb.gbshopmay.dao.ProductDao;
import ru.gb.gbshopmay.entity.Category;
import ru.gb.gbshopmay.entity.Manufacturer;
import ru.gb.gbshopmay.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
public class GbShopMayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GbShopMayApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner bootstrapEntities(ManufacturerDao manufacturerDao, CategoryDao categoryDao, ProductDao productDao) {
//
//        return (args) -> {
//            final String APPLE_COMPANY_NAME = "Apple";
//            final String ELECTRONIC_CATEGORY_NAME = "Electronic";
//
//            Manufacturer savedManufacturer = manufacturerDao.save(Manufacturer.builder()
//                    .name(APPLE_COMPANY_NAME)
//                    .build());
//            Category savedCategory = categoryDao.save(Category.builder()
//                    .title(ELECTRONIC_CATEGORY_NAME)
//                    .build());
//

//            productDao.save(Product.builder()
//                    .title("Bread")
//                    .cost(new BigDecimal("100.00"))
//                    .status(Status.ACTIVE)
//                    .manufactureDate(LocalDate.now())
//                    .manufacturer(manufacturerDao.findByName(APPLE_COMPANY_NAME).get())
//                    .categories(Set.of(categoryDao.findByTitle(ELECTRONIC_CATEGORY_NAME).get()))
//                    .build());
//        };
//    }

}
