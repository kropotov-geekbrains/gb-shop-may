package ru.gb.gbshopmay.web.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.gbapimay.review.dto.ReviewDto;
import ru.gb.gbshopmay.entity.Review;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final ProductMapper productMapper;

    public ReviewDto toReviewDto(Review review) {
        return ReviewDto.builder()
                .comment(review.getComment())
                .product(productMapper.toProductDto(review.getProduct()))
                .build();
    }
}
