package ru.gb.gbshopmay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gb.gbshopmay.entity.ProductImage;

import java.util.List;

/**
 * @author Artem Kropotov
 * created at 26.06.2022
 **/
public interface ProductImageDao extends JpaRepository<ProductImage, Long> {

    @Query(value = "SELECT product_image.path FROM product_image WHERE product_image.product_id = :id LIMIT 1", nativeQuery = true)
    String findImageNameByProductId(@Param("id") Long id);

    @Query(value = "SELECT product_image.path FROM product_image WHERE product_image.id = :id LIMIT 1", nativeQuery = true)
    String findImageNameByImageId(@Param("id") Long id);

    @Query(value = "SELECT product_image.id from product_image WHERE product_image.product_id = :id", nativeQuery = true)
    List<Long> findAllIdImagesByProductId(@Param("id") Long id);

}
