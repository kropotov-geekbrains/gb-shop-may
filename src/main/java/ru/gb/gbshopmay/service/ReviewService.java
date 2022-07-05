package ru.gb.gbshopmay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.gbapimay.review.dto.ReviewDto;
import ru.gb.gbshopmay.dao.ReviewDao;
import ru.gb.gbshopmay.entity.Review;
import ru.gb.gbshopmay.web.dto.mapper.ReviewMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewDao reviewDao;
    private final ReviewMapper reviewMapper;

    public Review save(ReviewDto reviewDto) {
        Review review = reviewMapper.toReview(reviewDto);
        return reviewDao.save(review);
    }


    @Transactional(readOnly = true)
    public ReviewDto findById(Long id) {
        return reviewMapper.toReviewDto(reviewDao.findById(id).orElse(null));
    }

    public List<ReviewDto> findAll() {
        return reviewDao.findAll().stream()
                .map(reviewMapper::toReviewDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        try {
            reviewDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }
}
