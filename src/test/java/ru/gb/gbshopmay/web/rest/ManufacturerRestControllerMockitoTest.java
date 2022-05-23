package ru.gb.gbshopmay.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.gbshopmay.service.ManufacturerService;
import ru.gb.gbshopmay.web.dto.ManufacturerDto;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ManufacturerRestControllerMockitoTest {

    public static final String APPLE_COMPANY_NAME = "Apple";
    public static final String MICROSOFT_COMPANY_NAME = "Microsoft";

    @Mock
    ManufacturerService manufacturerService;

    @InjectMocks
    ManufacturerRestController manufacturerRestController;

    MockMvc mockMvc;

    List<ManufacturerDto> manufacturers;

    @BeforeEach
    void setUp() {
        manufacturers = new ArrayList<>();
        manufacturers.add(new ManufacturerDto(1L, APPLE_COMPANY_NAME));
        manufacturers.add(new ManufacturerDto(2L, MICROSOFT_COMPANY_NAME));

        mockMvc = MockMvcBuilders.standaloneSetup(manufacturerRestController).build();
    }

    @Test
    void getManufacturerListMockMvcTest() throws Exception {
        // given
        given(manufacturerService.findAll()).willReturn(manufacturers);

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value(APPLE_COMPANY_NAME))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].name").value(MICROSOFT_COMPANY_NAME));
    }

    @Test
    void getManufacturerListTest() {
        // given
        given(manufacturerService.findAll()).willReturn(manufacturers);
        final int manufacturersSize = manufacturers.size();

        // when
        List<ManufacturerDto> manufacturerList = manufacturerRestController.getManufacturerList();

        // then
        then(manufacturerService).should().findAll();

        assertAll(
                () -> assertEquals(manufacturersSize, manufacturerList.size(), "Size must be equals " + manufacturersSize),
                () -> assertEquals(APPLE_COMPANY_NAME, manufacturerList.get(0).getName())
        );
    }

    // todo дз сделать методы проверки удаления и сохранения обычными unit тестами и mockmvc тестами (4 теста)
    @Test
    void deleteByIdMockMvcTest() throws Exception {
        mockMvc.perform(delete("/api/v1/manufacturer/1"))
                .andExpect(status().isNoContent());
    }
    @Test
    void deleteByIdTest() {
        manufacturerRestController.deleteById(1L);
        then(manufacturerService).should().deleteById(1L);
    }

    @Test
    void saveMockMvcTest() throws Exception {
        ManufacturerDto testManufacturer = new ManufacturerDto(2L, MICROSOFT_COMPANY_NAME);
        given(manufacturerService.save(any(ManufacturerDto.class))).willReturn(testManufacturer);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(testManufacturer);
        mockMvc.perform(post("/api/v1/manufacturer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void saveTest() {
        ManufacturerDto testManufacturer = new ManufacturerDto(2L, MICROSOFT_COMPANY_NAME);
        manufacturerRestController.handlePost(testManufacturer);
        then(manufacturerService).should().save(testManufacturer);
    }

}