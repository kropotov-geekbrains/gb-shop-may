package ru.gb.gbshopmay.web.dto.mapper;

import ru.gb.gbapimay.review.dto.ReviewDto;
import ru.gb.gbshopmay.entity.Review;

public interface ReviewMapper {

    Review toReview(ReviewDto reviewDto);

    ReviewDto toReviewDto(Review review);
}
