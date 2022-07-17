package ru.gb.gbshopmay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gb.gbshopmay.entity.Category;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
    @Query(value = "SELECT category.title FROM category JOIN product_category pc ON category.id = pc.category_id JOIN product ON pc.product_id = product.id WHERE product_id =:id ", nativeQuery = true)
    String findCategoryTitleByProductId(@Param("id") Long id);
}
