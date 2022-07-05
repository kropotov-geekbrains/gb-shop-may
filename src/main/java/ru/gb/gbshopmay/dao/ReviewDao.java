package ru.gb.gbshopmay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.Review;

public interface ReviewDao extends JpaRepository<Review, Long> {

}
