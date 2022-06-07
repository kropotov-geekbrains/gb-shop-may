package ru.gb.gbapimay.manufacturer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManufacturerDto {

    private Long id;
    private String name;
}
