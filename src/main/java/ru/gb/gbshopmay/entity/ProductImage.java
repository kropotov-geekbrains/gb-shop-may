package ru.gb.gbshopmay.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.gbshopmay.entity.common.BaseEntity;

import javax.persistence.*;

/**
 * @author Artem Kropotov
 * created at 26.06.2022
 **/
@Entity
@Table(name = "product_image")
@Setter
@Getter
@NoArgsConstructor
public class ProductImage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "path")
    private String path;

    @Builder
    public ProductImage(Long id, Product product, String path) {
        super(id);
        this.product = product;
        this.path = path;
    }
}
