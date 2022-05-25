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
import ru.gb.gbshopmay.entity.enums.Status;
import ru.gb.gbshopmay.web.dto.ManufacturerDto;
import ru.gb.gbshopmay.web.dto.ProductDto;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    public static final String IPHONE = "Iphone";
    public static final String MACBOOK = "Macbook";
    public static final String APPLE_COMPANY_NAME = "Apple";

    @Test
    @Order(1)
    public void saveTest() throws Exception {

        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ManufacturerDto.builder()
                                        .name(APPLE_COMPANY_NAME)
                                        .build())))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ProductDto.builder()
                                        .title(IPHONE)
                                        .cost(BigDecimal.valueOf(100))
                                        .status(Status.ACTIVE)
                                        .manufacturer(APPLE_COMPANY_NAME)
                                        .build())))
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
                .andExpect(jsonPath("$.[0].title").value(IPHONE))
                .andExpect(jsonPath("$.[0].cost").value(BigDecimal.valueOf(100.0)));
    }

    @Test
    @Order(2)
    public void handleUpdateTest() throws Exception {

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ProductDto.builder()
                                        .title(MACBOOK)
                                        .cost(BigDecimal.valueOf(1000))
                                        .status(Status.ACTIVE)
                                        .manufacturer(APPLE_COMPANY_NAME)
                                        .build())))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void deleteTest() throws Exception {
        assertEquals(2, productDao.findAll().size());

        mockMvc.perform(delete("/api/v1/product/{productId}", 1))
                .andExpect(status().isNoContent());

        assertEquals(1, productDao.findAll().size());
    }
}