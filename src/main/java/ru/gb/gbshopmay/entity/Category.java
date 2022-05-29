package ru.gb.gbshopmay.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "CATEGORY")
@EntityListeners(AuditingEntityListener.class)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;

//    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE)
//    private Set<Product> products;


//    @Version
//    @Column(name = "VERSION")
//    private int version;
//    @CreatedBy
//    @Column(name = "CREATED_BY", updatable = false)
//    private String createdBy;
//    @CreatedDate
//    @Column(name = "CREATED_DATE", updatable = false)
//    private LocalDateTime createdDate;
//    @LastModifiedBy
//    @Column(name = "LAST_MODIFIED_BY")
//    private String lastModifiedBy;
//    @LastModifiedDate
//    @Column(name = "LAST_MODIFIED_DATE")
//    private LocalDateTime lastModifiedDate;


    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + title + '\'' +
                '}';
    }
}
