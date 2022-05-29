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
import ru.gb.gbshopmay.dao.ProductDao;
import ru.gb.gbshopmay.entity.enums.Status;
import ru.gb.gbshopmay.web.dto.ManufacturerDto;
import ru.gb.gbshopmay.web.dto.ProductDto;

import java.math.BigDecimal;

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
    ObjectMapper objectMapper;

    public static final String MICROSOFT_COMPANY_NAME = "Microsoft";
    public static final String MICROSOFT_PRODUCT_NAME = "Windows 98";


    @Test
    @Order(1)
    public void saveTest() throws Exception {
        mockMvc.perform(post("/api/v1/manufacturer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(ManufacturerDto.builder()
                                .name(MICROSOFT_COMPANY_NAME)
                                .build())))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(ProductDto.builder()
                                .title(MICROSOFT_PRODUCT_NAME)
                                .cost(BigDecimal.valueOf(1500.0))
                                .status(Status.ACTIVE)
                                .manufacturer(MICROSOFT_COMPANY_NAME)
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
                .andExpect(jsonPath("$.[0].title").value(MICROSOFT_PRODUCT_NAME))
                .andExpect(jsonPath("$.[0].cost").value(BigDecimal.valueOf(1500.0)))
                .andExpect(jsonPath("$.[0].manufacturer").value(MICROSOFT_COMPANY_NAME));
    }
}