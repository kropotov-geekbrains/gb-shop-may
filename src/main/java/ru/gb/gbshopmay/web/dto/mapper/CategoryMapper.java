package ru.gb.gbshopmay.web.dto.mapper;

import org.mapstruct.Mapper;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.gb.gbshopmay.entity.Category;

@Mapper
public interface CategoryMapper {
    Category toCategory(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);
}
