package ru.gb.gbshopmay.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.gbapimay.manufacturer.dto.ManufacturerDto;
import ru.gb.gbshopmay.service.ManufacturerService;

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

    @Test
    void handlePostTest() throws Exception {
        given(manufacturerService.save(any())).will(
                (invocation) -> {
                    ManufacturerDto manufacturerDto = invocation.getArgument(0);

                    if (manufacturerDto == null) {
                        return null;
                    }

                    return ManufacturerDto.builder()
                            .id(manufacturerDto.getId())
                            .name(manufacturerDto.getName())
                            .build();
                });

        mockMvc.perform(post("/api/v1/manufacturer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"test\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteByIdTest() throws Exception {

        mockMvc.perform(delete("/api/v1/manufacturer/{manufacturerId}", 1))
                .andExpect(status().isNoContent());

        given(manufacturerService.save(any())).will(
                (invocation) -> {
                    ManufacturerDto manufacturerDto = invocation.getArgument(0);

                    if (manufacturerDto == null) {
                        return null;
                    }

                    return ManufacturerDto.builder()
                            .id(manufacturerDto.getId())
                            .name(manufacturerDto.getName())
                            .build();
                });

        mockMvc.perform(post("/api/v1/manufacturer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"test\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/v1/manufacturer/{manufacturerId}", 1))
                .andExpect(status().isNoContent());
    }
}