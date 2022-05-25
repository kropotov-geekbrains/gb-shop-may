package ru.gb.gbshopmay.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.gb.gbshopmay.config.ShopConfig;
import ru.gb.gbshopmay.entity.Manufacturer;

import java.util.List;

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
        Manufacturer apple = Manufacturer.builder()
                .name(APPLE_COMPANY_NAME)
                .build();
        Manufacturer microsoft = Manufacturer.builder()
                .name(MICROSOFT_COMPANY_NAME)
                .build();

        Manufacturer savedManufacturer1 = manufacturerDao.save(apple);
        Manufacturer savedManufacturer2 = manufacturerDao.save(microsoft);
        List<Manufacturer> manufacturerList = manufacturerDao.findAll();
        assertAll(
                () -> assertEquals(1L, manufacturerList.get(0).getId()),
                () -> assertEquals(APPLE_COMPANY_NAME, manufacturerList.get(0).getName()),
                () -> assertEquals(2L, manufacturerList.get(1).getId()),
                () -> assertEquals(MICROSOFT_COMPANY_NAME, manufacturerList.get(1).getName()),
                () -> assertEquals(0, manufacturerList.get(0).getVersion()),
                () -> assertEquals("User", manufacturerList.get(0).getCreatedBy()),
                () -> assertNotNull(manufacturerList.get(0).getCreatedDate()),
                () -> assertEquals("User", manufacturerList.get(0).getLastModifiedBy()),
                () -> assertNotNull(manufacturerList.get(0).getLastModifiedDate())
        );
    }

    @Test
    public void deleteTest() {
        Manufacturer manufacturer = Manufacturer.builder()
                .name(APPLE_COMPANY_NAME)
                .build();
        Manufacturer savedManufacturer = manufacturerDao.save(manufacturer);
        assertEquals(1, manufacturerDao.findAll().size());

        manufacturerDao.deleteById(1L);
        assertEquals(0, manufacturerDao.findAll().size());
    }
}