package ru.gb.gbshopmay.dao;

//import jdk.internal.org.jline.reader.ScriptEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.gb.gbshopmay.config.ShopConfig;
import ru.gb.gbshopmay.entity.Manufacturer;
import ru.gb.gbshopmay.web.dto.ManufacturerDto;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DataJpaTest
@Import(ShopConfig.class)
class ManufacturerDaoDataJpaTest {

    public static final String APPLE_COMPANY_NAME = "Apple";
    public static final String MICROSOFT_COMPANY_NAME = "Microsoft";

    @Autowired
    ManufacturerDao manufacturerDao;

//    EntityManager entityManager;
    @Autowired
    private TestEntityManager entityManager;

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
    void getManufacturerListTest() {
        entityManager.persist(Manufacturer.builder()
                .name(APPLE_COMPANY_NAME)
                .build());
        entityManager.persist(Manufacturer.builder()
                .name(MICROSOFT_COMPANY_NAME)
                .build());

        List<Manufacturer> manufacturerList = manufacturerDao.findAll();

        assertAll(
//                () -> assertEquals(manufacturersSize, manufacturerList.size(), "Size must be equals " + manufacturersSize),
                () -> assertEquals(APPLE_COMPANY_NAME, manufacturerList.get(0).getName())
        );
    }

    @Test
    public void deleteTest() {
        Manufacturer manufacturer = Manufacturer.builder()
                .name(APPLE_COMPANY_NAME)
                .build();

        Manufacturer savedManufacturer = manufacturerDao.save(manufacturer);

        manufacturerDao.delete(savedManufacturer);

        assertAll(
                () -> assertNotNull(savedManufacturer.getName())
        );
    }

}