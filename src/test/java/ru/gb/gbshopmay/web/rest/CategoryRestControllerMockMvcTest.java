package ru.gb.gbshopmay.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryRestController.class)
public class CategoryRestControllerMockMvcTest {

    public static final String ELECTRONIC_CATEGORY_NAME = "Cellphones";
    public static final String FOODSTUFF_CATEGORY_NAME = "Vegetables";

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
        categories.add(new CategoryDto(1L, ELECTRONIC_CATEGORY_NAME));
        categories.add(new CategoryDto(2L, FOODSTUFF_CATEGORY_NAME));
    }

    @Test
    void getManufacturerListMockMvcTest() throws Exception {
        given(categoryService.findAll()).willReturn(categories);
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(ELECTRONIC_CATEGORY_NAME))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].title").value(FOODSTUFF_CATEGORY_NAME));
    }

    @Test
    void handlePostTest() throws Exception {
        given(categoryService.save(any())).will(
                (invocation) -> {
                    CategoryDto categoryDto = invocation.getArgument(0);
                    if (categoryDto == null) {
                        return null;
                    }
                    return CategoryDto.builder()
                            .id(categoryDto.getId())
                            .title(categoryDto.getTitle())
                            .build();
                });

        mockMvc.perform(post("/api/v1/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"title\": \"test\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteByIdTest() throws Exception {

        mockMvc.perform(delete("/api/v1/category/{categoryId}", 1))
                .andExpect(status().isNoContent());
        given(categoryService.save(any())).will(
                (invocation) -> {
                    CategoryDto categoryDto = invocation.getArgument(0);

                    if (categoryDto == null) {
                        return null;
                    }

                    return CategoryDto.builder()
                            .id(categoryDto.getId())
                            .title(categoryDto.getTitle())
                            .build();
                });
        mockMvc.perform(post("/api/v1/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"test\"}"))
                .andExpect(status().isCreated());
        mockMvc.perform(delete("/api/v1/category/{categoryId}", 1))
                .andExpect(status().isNoContent());
    }
}
