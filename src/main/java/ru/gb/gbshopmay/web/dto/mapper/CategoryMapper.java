package ru.gb.gbshopmay.web.dto.mapper;

import org.mapstruct.Mapper;
import ru.gb.gbshopmay.entity.Category;
import ru.gb.gbshopmay.web.dto.CategoryDto;

@Mapper
public interface CategoryMapper {
    Category toCategory(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);
}
