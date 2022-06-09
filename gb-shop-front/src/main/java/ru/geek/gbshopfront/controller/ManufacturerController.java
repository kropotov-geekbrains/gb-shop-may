package ru.geek.gbshopfront.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapimay.manufacturer.api.ManufacturerGateway;
import ru.gb.gbapimay.manufacturer.dto.ManufacturerDto;
@RequiredArgsConstructor
@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController {

    private final ManufacturerGateway manufacturerGateway;


    @GetMapping("/list")
    public String showManufacturersList(Model model) {
        model.addAttribute("manufacturers", manufacturerGateway.getManufacturerList());
        return "manufacturer-list";
    }

//    @GetMapping("/{manufacturerId}")
//    public ResponseEntity<?> getManufacturer(@PathVariable("manufacturerId") Long id) {
//        return manufacturerGateway.getManufacturer(id);
//    }
//
//    @PostMapping
//    public ResponseEntity<?> handlePost(@Validated @RequestBody ManufacturerDto manufacturerDto) {
//        return manufacturerGateway.handlePost(manufacturerDto);
//    }
//
//    @PutMapping("/{manufacturerId}")
//    public ResponseEntity<?> handleUpdate(@PathVariable("manufacturerId") Long id, @Validated @RequestBody ManufacturerDto manufacturerDto) {
//        return manufacturerGateway.handleUpdate(id, manufacturerDto);
//
//    }
//
//    @DeleteMapping("/{manufacturerId}")
//    public void deleteById(@PathVariable("manufacturerId") Long id) {
//        manufacturerGateway.deleteById(id);
//    }
}
