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
import ru.gb.gbshopmay.dao.CategoryDao;
import ru.gb.gbshopmay.web.dto.CategoryDto;
import ru.gb.gbshopmay.web.dto.ManufacturerDto;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryRestControllerIntegTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ObjectMapper objectMapper;

    public static final String APPLE_CATEGORY_NAME = "Apple";
    public static final String MICROSOFT_CATEGORY_NAME = "Microsoft";

    @Test
    @Order(1)
    public void saveTest() throws Exception {

        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(CategoryDto.builder()
                                .title(APPLE_CATEGORY_NAME)
                                .build())))
                .andExpect(status().isCreated());

        assertEquals(1, categoryDao.findAll().size());
    }

    @Test
    @Order(2)
    public void findAllTest() throws Exception {

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(APPLE_CATEGORY_NAME));
    }

}