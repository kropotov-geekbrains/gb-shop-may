package ru.gb.gbshopmay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
