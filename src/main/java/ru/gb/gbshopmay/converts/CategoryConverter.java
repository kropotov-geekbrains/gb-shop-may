package ru.gb.gbshopmay.converts;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.gb.gbshopmay.dao.CategoryDao;
import ru.gb.gbshopmay.web.dto.mapper.CategoryMapper;

/**
 * @author Artem Kropotov
 * created at 26.06.2022
 **/
@Component
@RequiredArgsConstructor
public class CategoryConverter implements Converter<String, CategoryDto> {

    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;


    @Override
    public CategoryDto convert(String source) {
        return categoryMapper.toCategoryDto(categoryDao.findById(Long.valueOf(source)).orElse(null));
    }
}
