package ru.gb.gbshopmay.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.gb.gbapimay.common.enums.Status;
import ru.gb.gbshopmay.entity.common.InfoEntity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table (name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product extends InfoEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "cart_product",
//    joinColumns = @JoinColumn(name = "product_id"),
//    inverseJoinColumns = @JoinColumn(name = "cart_id"))
//    private Set<Cart> carts;

    //    @Singular
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "product")
    private List<ProductImage> images;

    public void addImage(ProductImage productImage) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(productImage);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId()+
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", manufactureDate=" + manufactureDate +
//                ", manufacturer=" + manufacturer.getName() +
                "}\n";
    }
    @Builder
    public Product(Long id, int version, String createdBy, LocalDateTime createdDate, String lastModifiedBy,
                   LocalDateTime lastModifiedDate, String title, BigDecimal cost, LocalDate manufactureDate,
                   Manufacturer manufacturer, Status status, Set<Category> categories, List<ProductImage> images) {
        super(id, version, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.title = title;
        this.cost = cost;
        this.manufactureDate = manufactureDate;
        this.manufacturer = manufacturer;
        this.status = status;
        this.categories = categories;
        this.images = images;
    }
}
