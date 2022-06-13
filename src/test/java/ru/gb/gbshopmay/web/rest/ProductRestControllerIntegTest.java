package ru.gb.gbshopmay.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.gb.gbapimay.common.enums.Status;
import ru.gb.gbapimay.manufacturer.dto.ManufacturerDto;
import ru.gb.gbapimay.product.dto.ProductDto;
import ru.gb.gbshopmay.dao.CategoryDao;
import ru.gb.gbshopmay.dao.ManufacturerDao;
import ru.gb.gbshopmay.dao.ProductDao;
import java.math.BigDecimal;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    CategoryDao categoryDao;

    @Autowired
    ObjectMapper objectMapper;

    public static final String MACBOOK = "MacBook";
    public static final String APPLE = "Apple";
    Set<CategoryDto> categories;
    public static final String FOOD = "Food";

    @Test
    @Order(1)
//    @Disabled
    public void saveTest() throws Exception {

        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ManufacturerDto.builder()
                                        .name(APPLE)
                                        .build())))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title(FOOD)
                                        .build())))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ProductDto.builder()
                                        .title(MACBOOK)
                                        .cost(BigDecimal.valueOf(1))
                                        .status(Status.ACTIVE)
                                        .manufacturer(APPLE)
                                        .categories(categories)
                                        .build())))
                .andExpect(status().isCreated());

        assertEquals(1, productDao.findAll().size());
    }

    @Test
    @Order(2)
    @Disabled
    public void findAllTest() throws Exception {

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(MACBOOK))
                .andExpect(jsonPath("$.[0].cost").value(BigDecimal.valueOf(1.0)));
    }
}