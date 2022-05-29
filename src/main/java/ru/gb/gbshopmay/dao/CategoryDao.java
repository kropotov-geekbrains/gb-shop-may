package ru.gb.gbshopmay.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.Category;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Category findByTitleLike(String name);
    Optional<Category> findByTitle(String name);
}
