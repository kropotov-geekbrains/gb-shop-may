package ru.gb.gbshopmay.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.gb.gbshopmay.config.ShopConfig;
import ru.gb.gbshopmay.entity.Manufacturer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ShopConfig.class)
class ManufacturerDaoDataJpaTest {

    public static final String APPLE_COMPANY_NAME = "Apple";
    public static final String MICROSOFT_COMPANY_NAME = "Microsoft";

    @Autowired
    ManufacturerDao manufacturerDao;

    List<Manufacturer> manufacturers;

    @BeforeEach
    void setUp() {
        manufacturers = new ArrayList<>();
        manufacturers.add(Manufacturer.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .build());
        manufacturers.add(Manufacturer.builder()
                .id(2L)
                .name(MICROSOFT_COMPANY_NAME)
                .build());
    }

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

    // todo дз findAll, delete, update
    // todo дз* попробовать через persistenceContext(либо через Autowoired) (через EntityManager)

    @Test
    public void findAllTest() {
        Manufacturer appleManufacturer = Manufacturer.builder()
                .name(APPLE_COMPANY_NAME)
                .build();
        Manufacturer microsoftManufacturer = Manufacturer.builder()
                .name(MICROSOFT_COMPANY_NAME)
                .build();

        manufacturerDao.save(appleManufacturer);
        manufacturerDao.save(microsoftManufacturer);

        List<Manufacturer> manufacturers = manufacturerDao.findAll();

        assertAll(
                () -> assertEquals(2, manufacturers.size()),
                () -> assertEquals(1L, manufacturers.get(0).getId()),
                () -> assertEquals(2L, manufacturers.get(1).getId()),
                () -> assertEquals(APPLE_COMPANY_NAME, manufacturers.get(0).getName()),
                () -> assertEquals(MICROSOFT_COMPANY_NAME, manufacturers.get(1).getName()),
                () -> assertEquals(0, manufacturers.get(0).getVersion()),
                () -> assertEquals(0, manufacturers.get(1).getVersion()),
                () -> assertEquals("User", manufacturers.get(0).getCreatedBy()),
                () -> assertEquals("User", manufacturers.get(1).getCreatedBy()),
                () -> assertNotNull(manufacturers.get(0).getCreatedDate()),
                () -> assertNotNull(manufacturers.get(1).getCreatedDate()),
                () -> assertEquals("User", manufacturers.get(0).getLastModifiedBy()),
                () -> assertEquals("User", manufacturers.get(1).getLastModifiedBy()),
                () -> assertNotNull(manufacturers.get(0).getLastModifiedDate()),
                () -> assertNotNull(manufacturers.get(1).getLastModifiedDate())
        );
    }

    @Test
    public void deleteTest() {
        Manufacturer manufacturer = Manufacturer.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .build();

        manufacturerDao.save(manufacturer);
//        manufacturerDao.deleteAll();
        manufacturerDao.deleteById(1L);
    }

    @Test
    public void updateTest() {
        Manufacturer appleManufacturer = Manufacturer.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .build();
        Manufacturer microsoftManufacturer = Manufacturer.builder()
                .id(1L)
                .name(MICROSOFT_COMPANY_NAME)
                .build();

        Manufacturer savedAppleManufacturer = manufacturerDao.save(appleManufacturer);
        assertAll(
                () -> assertEquals(1L, appleManufacturer.getId()),
                () -> assertEquals(APPLE_COMPANY_NAME, savedAppleManufacturer.getName())
        );

        Manufacturer savedMicrosoftManufacturer = manufacturerDao.save(microsoftManufacturer);
        assertAll(
                () -> assertEquals(1L, microsoftManufacturer.getId()),
                () -> assertEquals(MICROSOFT_COMPANY_NAME, savedMicrosoftManufacturer.getName())
        );
    }
}