package ru.gb.gbshopmay.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ManufacturerRestControllerMockitoTest {

    public static final String APPLE_COMPANY_NAME = "Apple";
    public static final String MICROSOFT_COMPANY_NAME = "Microsoft";

    @Mock
    ManufacturerService manufacturerService;

    @InjectMocks
    ManufacturerRestController manufacturerRestController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

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
    void ManufacturerSaveTest() {
        // given
        ManufacturerDto manufacturerDto = new ManufacturerDto(3L, "Honor");
        // when
        manufacturerRestController.handlePost(manufacturerDto);
//        manufacturerService.save(manufacturerDto);
        // then
        then(manufacturerService).should().save(manufacturerDto);
        assertAll(
                () -> assertEquals(3L, manufacturerDto.getId()),
                () -> assertEquals("Honor", manufacturerDto.getName())
//               () -> assertEquals(handlePost, "/api/v1/manufacturer/3" )
        );
    }

    @Test
    void ManufacturerDeleteTest(){
        // when
        manufacturerRestController.deleteById(1L);
        // then
        then(manufacturerService).should().deleteById(1L);
    }

    @Test
    void ManufacturerSaveMockMvcTest() throws Exception {
        //given
        ManufacturerDto manufacturerDto = new ManufacturerDto(3L, "Honor");
        given(manufacturerService.save(any(ManufacturerDto.class))).willReturn(manufacturerDto);
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(manufacturerDto));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/manufacturer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" :\"Honor\",\"id\":\"3\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteByIdMockMvcTest() throws Exception {
        mockMvc.perform(delete("/api/v1/manufacturer/1"))
                .andExpect(status().isNoContent());
    }
}