package ru.gb.gbshopmay.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.gbshopmay.service.ManufacturerService;
import ru.gb.gbshopmay.web.dto.ManufacturerDto;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
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
    void manufacturerSaveTest() throws Exception {
        // given
                ManufacturerDto manufacturer = ManufacturerDto.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .build();
        given(manufacturerService.save(manufacturer)).willReturn(manufacturer);

        // when
        ManufacturerDto returnedManufacturerDto = manufacturerService.save(manufacturer);

        // then
        then(manufacturerService).should().save(any());
        assertAll(
                () -> assertEquals(1L, returnedManufacturerDto.getId()),
                () -> assertEquals(APPLE_COMPANY_NAME, returnedManufacturerDto.getName())
        );

    }

    @Test
    void manufacturerDeleteTest() throws Exception {

        // when
        manufacturerService.deleteById(1L);
        // then
 //       then().should().deleteById(1L);
    }

    @Test
    public void deleteByIdManufacturerMockMvcTest() throws Exception {

        mockMvc.perform(delete("/api/v1/manufacturer/{manufacturerId}", 1))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/manufacturer/{manufacturerId}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void postManufacturerSaveMockMvcTest() throws Exception {
        given(manufacturerService.save(any())).willReturn(manufacturers.get(0));

        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"${APPLE_COMPANY_NAME}\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}