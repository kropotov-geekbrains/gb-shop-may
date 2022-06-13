package ru.gb.gbapimay.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.gb.gbapimay.product.dto.ProductDto;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    @JsonProperty(value = "id")
    private Long id;
    private String title;
//    private Set<ProductDto> products;
}
