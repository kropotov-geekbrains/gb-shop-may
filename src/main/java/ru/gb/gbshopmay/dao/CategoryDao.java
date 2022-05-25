package ru.gb.gbshopmay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
    List<Category> findAllByTitleContaining(String title);
}
