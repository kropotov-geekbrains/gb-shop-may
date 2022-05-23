package ru.gb.gbshopmay.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.gbshopmay.dao.ManufacturerDao;
import ru.gb.gbshopmay.entity.Manufacturer;
import ru.gb.gbshopmay.web.dto.ManufacturerDto;
import ru.gb.gbshopmay.web.dto.mapper.ManufacturerMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceMockitoTest {

    public static final String APPLE_COMPANY_NAME = "Apple";
    public static final String MICROSOFT_COMPANY_NAME = "Microsoft";

    @Mock
    ManufacturerDao manufacturerDao;

    @Mock
    ManufacturerMapper manufacturerMapper;

    @InjectMocks
    ManufacturerService manufacturerService;

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
    void findAllTest() {
        // given
        given(manufacturerDao.findAll()).willReturn(manufacturers);
        given(manufacturerMapper.toManufacturerDto(any())).will(
                (invocation) -> {
                    Manufacturer manufacturer = (Manufacturer) invocation.getArgument(0);

                    if (manufacturer == null) {
                        return null;
                    }

                    return ManufacturerDto.builder()
                            .id(manufacturer.getId())
                            .name(manufacturer.getName())
                            .build();
                });
        final int manufacturersSize = manufacturers.size();

        // when
        List<ManufacturerDto> manufacturerList = manufacturerService.findAll();

        // then
        then(manufacturerDao).should().findAll();

        assertAll(
                () -> assertEquals(manufacturersSize, manufacturerList.size(), "Size must be equals " + manufacturersSize),
                () -> assertEquals(APPLE_COMPANY_NAME, manufacturerList.get(0).getName())
        );
    }

    @Test
    void deleteByIdTest() {
        manufacturerService.deleteById(1L);
        then(manufacturerDao).should().deleteById(1L);
    }

    @Test
    void saveTest() {
        Manufacturer testManufacturer = Manufacturer.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .build();
        ManufacturerDto testManufacturerDto = ManufacturerDto.builder()
                .id(testManufacturer.getId())
                .name(testManufacturer.getName())
                .build();
        given(manufacturerMapper.toManufacturer(any(ManufacturerDto.class))).willReturn(testManufacturer);
        manufacturerService.save(testManufacturerDto);
        then(manufacturerDao).should().save(testManufacturer);
    }

}