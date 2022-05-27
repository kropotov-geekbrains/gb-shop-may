package ru.gb.gbshopmay.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbshopmay.dao.ManufacturerDao;
import ru.gb.gbshopmay.dao.ProductDao;
import ru.gb.gbshopmay.entity.Manufacturer;
import ru.gb.gbshopmay.entity.enums.Status;
import ru.gb.gbshopmay.web.dto.ManufacturerDto;
import ru.gb.gbshopmay.web.dto.ProductDto;
import ru.gb.gbshopmay.web.dto.mapper.ManufacturerMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRestControllerIntegTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductDao productDao;

    @Autowired
    ManufacturerDao manufacturerDao;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ManufacturerMapper manufacturerMapper;

    private final String FIRST_TEST_PRODUCT = "Test1";
    private final String SECOND_TEST_PRODUCT = "Test2";
    private final String APPLE_COMPANY_NAME = "Apple";

    @Test
    @Order(1)
    public void saveTest() throws Exception {

        ManufacturerDto manufacturerDto = ManufacturerDto.builder()
                .name(APPLE_COMPANY_NAME)
                .build();

        Manufacturer manufacturer = manufacturerDao.save(manufacturerMapper.toManufacturer(manufacturerDto));

        System.out.println(manufacturer);

        ProductDto productDto = ProductDto.builder()
                .title(FIRST_TEST_PRODUCT)
                .cost(BigDecimal.valueOf(1))
                .manufactureDate(LocalDate.EPOCH)
                .status(Status.ACTIVE)
                .manufacturer(manufacturer.getName())
                .build();
        String productDtoString = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoString))
                .andExpect(status().isCreated());

        assertEquals(1, productDao.findAll().size());
    }

    @Test
    @Order(2)
    public void findAllTest() throws Exception {

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(FIRST_TEST_PRODUCT))
                .andExpect(jsonPath("$.[0].cost").value(BigDecimal.valueOf(1.0).toString()))
                .andExpect(jsonPath("$.[0].manufactureDate").value(LocalDate.EPOCH.toString()))
                .andExpect(jsonPath("$.[0].status").value(Status.ACTIVE.toString()))
                .andExpect(jsonPath("$.[0].manufacturer").value(APPLE_COMPANY_NAME));
    }
}