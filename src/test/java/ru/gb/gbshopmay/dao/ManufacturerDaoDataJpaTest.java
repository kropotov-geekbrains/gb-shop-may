package ru.gb.gbshopmay.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.gb.gbshopmay.config.ShopConfig;
import ru.gb.gbshopmay.entity.Manufacturer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ShopConfig.class)
class ManufacturerDaoDataJpaTest {

    public static final String APPLE_COMPANY_NAME = "Apple";
    public static final String MICROSOFT_COMPANY_NAME = "Microsoft";

    @Autowired
    ManufacturerDao manufacturerDao;

    @Test
    public void saveTest() {
        Manufacturer manufacturer = Manufacturer.builder()
                .name(APPLE_COMPANY_NAME)
                .build();

        Manufacturer savedManufacturer = manufacturerDao.save(manufacturer);

        assertAll(
                () -> assertEquals(1L, savedManufacturer.getId()),
                () -> assertEquals(APPLE_COMPANY_NAME, savedManufacturer.getName()),
                () -> assertEquals(0, savedManufacturer.getVersion()),
                () -> assertEquals("User", savedManufacturer.getCreatedBy()),
                () -> assertNotNull(savedManufacturer.getCreatedDate()),
                () -> assertEquals("User", savedManufacturer.getLastModifiedBy()),
                () -> assertNotNull(savedManufacturer.getLastModifiedDate())

        );
    }

    @Test
    public void findAllTest() {
        Manufacturer manufacturer1 = Manufacturer.builder()
                .name(APPLE_COMPANY_NAME)
                .build();
        manufacturerDao.save(manufacturer1);

        Manufacturer manufacturer2 = Manufacturer.builder()
                .name(MICROSOFT_COMPANY_NAME)
                .build();
        manufacturerDao.save(manufacturer2);

        List<Manufacturer> manufacturerList = manufacturerDao.findAll();

        assertAll(
                () -> assertEquals(1L, manufacturerList.get(0).getId()),
                () -> assertEquals(APPLE_COMPANY_NAME, manufacturerList.get(0).getName()),
                () -> assertEquals(2L, manufacturerList.get(1).getId()),
                () -> assertEquals(MICROSOFT_COMPANY_NAME, manufacturerList.get(1).getName())

                );

    }

    @Test
    public void deleteTest() {
        Manufacturer manufacturer = Manufacturer.builder()
                .name(APPLE_COMPANY_NAME)
                .build();
        Manufacturer savedManufacturer = manufacturerDao.save(manufacturer);

        manufacturerDao.deleteById(savedManufacturer.getId());
        Optional<Manufacturer> deletedManufacturer = manufacturerDao.findByName(savedManufacturer.getName());

        assertAll(
                () -> assertEquals(Optional.empty(), deletedManufacturer)
        );
    }

    @Test
    public void updateTest() {
        Manufacturer manufacturer = Manufacturer.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .build();
        Manufacturer savedManufacturer = manufacturerDao.save(manufacturer);

        Manufacturer manufacturer2 = Manufacturer.builder()
                .id(1L)
                .name(MICROSOFT_COMPANY_NAME)
                .build();
        Manufacturer updateManufacturer = manufacturerDao.save(manufacturer2);

        Manufacturer testManufacturer = manufacturerDao.getById(1L);

        assertAll(
                () -> assertEquals(1L, testManufacturer.getId()),
                () -> assertEquals(testManufacturer.getName(), updateManufacturer.getName()),
                () -> assertEquals(testManufacturer.getId(), updateManufacturer.getId())
        );
    }

}