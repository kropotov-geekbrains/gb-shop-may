package ru.gb.gbshopmay.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.gb.gbapimay.manufacturer.dto.ManufacturerDto;
import ru.gb.gbshopmay.entity.Category;
import ru.gb.gbshopmay.service.CategoryService;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CategoryRestController.class)
public class CategoryControllerMockMvcTest {

    public static final String SOFT_CATEGORY = "Soft";
    public static final String FOOD_CATEGORY = "Food";


    @MockBean
    CategoryService categoryService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    List<CategoryDto> categories;

    @BeforeEach
    void setUp() throws Exception {
        categories = new ArrayList<>();
        categories.add(new CategoryDto(1L, SOFT_CATEGORY));
        categories.add(new CategoryDto(2L, FOOD_CATEGORY));
    }


    @Test
//    @Disabled
    public void findAllTest() throws Exception {
        Mockito.when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].title").value(FOOD_CATEGORY))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(SOFT_CATEGORY));

    }


    @Test
//    @Disabled
    public void findByIdTest() throws Exception {

        given(categoryService.findById(2L)).willReturn(new CategoryDto(2L, "Food"));

        mockMvc.perform(get("/api/v1/category/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("title")))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.title").value("Food"));

    }


    @Test
//    @Disabled
    public void saveTest() throws Exception {
        Mockito.when(categoryService.save(any(CategoryDto.class)))
                .thenReturn(CategoryDto.builder()
                        .id(1L)
                        .title(SOFT_CATEGORY)
                        .build());

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title(SOFT_CATEGORY)
                                        .build())))
                .andExpect(status().isCreated());
    }



    @Test
//    @Disabled
    public void deleteByIdTest() throws Exception {

        mockMvc.perform(delete("/api/v1/category/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/category/2"))
                .andExpect(status().isNotFound());

    }

}
