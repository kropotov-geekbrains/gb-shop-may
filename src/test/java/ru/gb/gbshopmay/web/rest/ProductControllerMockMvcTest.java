//package ru.gb.gbshopmay.web.rest;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.gb.gbapimay.category.dto.CategoryDto;
//import ru.gb.gbapimay.common.enums.Status;
//import ru.gb.gbapimay.product.dto.ProductDto;
//import ru.gb.gbshopmay.dao.CategoryDao;
//import ru.gb.gbshopmay.dao.ManufacturerDao;
//import ru.gb.gbshopmay.dao.ProductDao;
//import ru.gb.gbshopmay.entity.Category;
//import ru.gb.gbshopmay.service.ProductService;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(ProductRestController.class)
//public class ProductControllerMockMvcTest {
//
//    public static final String MACBOOK = "MacBook";
//    public static final String APPLE = "Apple";
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    ProductService productService;
//
//    @Autowired
//    ProductDao productDao;
//
//    @Autowired
//    ManufacturerDao manufacturerDao;
//
//    @Autowired
//    CategoryDao categoryDao;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    List<ProductDto> products;
//    Set<CategoryDto> categories;
//
//
//
//    @BeforeEach
//    void setUp() {
//        products = new ArrayList<>();
//
//        products.add(new ProductDto(1L, MACBOOK, new BigDecimal("1500.0"), LocalDate.of(2022, 1, 15), Status.ACTIVE, "Apple", categories));
//        products.add(new ProductDto(2L, APPLE, new BigDecimal("150.0"), LocalDate.of(2022, 2, 15), Status.ACTIVE, "Apple", categories));
//    }
//
//    @Test
//    public void findAllTest() throws Exception {
//
////        given(productService.findById(2L)).willReturn(new ProductDto(2L, APPLE, new BigDecimal("150.0"), LocalDate.of(2022, 2, 15), Status.ACTIVE, "Apple", categories));
//        Mockito.when(productService.findAll()).thenReturn(products);
//
//        mockMvc.perform(get("/api/v1/product"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("id")))
//                .andExpect(jsonPath("$.[1].id").value("2"))
//                .andExpect(jsonPath("$.[1].title").value(MACBOOK))
//                .andExpect(jsonPath("$.[1].cost").value("150.0"))
//                .andExpect(jsonPath("$.[1].manufactureDate").value("15.02.2022"))
//                .andExpect(jsonPath("$.[1].manufacturer").value("Apple"));
////                .andExpect(jsonPath("$.[0].id").value("1"))
////                .andExpect(jsonPath("$.[0].title").value(APPLE))
////                .andExpect(jsonPath("$.[0].cost").value("1500.0"))
////                .andExpect(jsonPath("$.[0].manufactureDate").value("15.01.2022"))
////                .andExpect(jsonPath("$.[0].manufacturer").value("Apple"));
//
//    }
//
//    @Test
//    public void deleteByIdTest() throws Exception {
//
//        mockMvc.perform(delete("/api/v1/product/2")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        mockMvc.perform(get("/api/v1/product/2"))
//                .andExpect(status().isNotFound());
//
//    }
//}
