package ru.gb.gbapimay.manufacturer.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapimay.manufacturer.dto.ManufacturerDto;

import java.util.List;
@FeignClient(url = "http://localhost:8080/api/v1/manufacturer", name = "ManufacturerGateway")
public interface ManufacturerGateway {
    @GetMapping
    List<ManufacturerDto> getManufacturerList();

    @GetMapping("/{manufacturerId}")
    ResponseEntity<ManufacturerDto> getManufacturer(@PathVariable("manufacturerId") Long id);

    @PostMapping
    ResponseEntity<ManufacturerDto> handlePost(@Validated @RequestBody ManufacturerDto manufacturerDto);

    @PutMapping("/{manufacturerId}")
    ResponseEntity<ManufacturerDto> handleUpdate(@PathVariable("manufacturerId") Long id,
                                                 @Validated @RequestBody ManufacturerDto manufacturerDto);

    @DeleteMapping("/{manufacturerId}")
    void deleteById(@PathVariable("manufacturerId") Long id);
}
