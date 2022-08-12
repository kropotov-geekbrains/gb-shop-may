package ru.gb.gbshopmay.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.gb.gbshopmay.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryRestController.class)
class CategoryRestControllerMockMvcTest {

    public static final String APPLE_CATEGORY_NAME = "electronics";
    public static final String MICROSOFT_CATEGORY_NAME = "computers";

    @MockBean
    CategoryService categoryService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    List<CategoryDto> categories;

    @BeforeEach
    void setUp() {
        categories = new ArrayList<>();
        categories.add(new CategoryDto(1L, APPLE_CATEGORY_NAME));
        categories.add(new CategoryDto(2L, MICROSOFT_CATEGORY_NAME));
    }

    @Test
    public void findAllTest() throws Exception {

        Mockito.when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(APPLE_CATEGORY_NAME))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].title").value(MICROSOFT_CATEGORY_NAME));
    }

    @Test
    public void saveTest() throws Exception {
        Mockito.when(categoryService.save(any(CategoryDto.class)))
                .thenReturn(CategoryDto.builder()
                        .id(1L)
                        .title(APPLE_CATEGORY_NAME)
                        .build());

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title(APPLE_CATEGORY_NAME)
                                        .build())))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteTest() throws Exception {

        mockMvc.perform(delete("/api/v1/category/1"))
                .andExpect(status().isNoContent());
    }

}