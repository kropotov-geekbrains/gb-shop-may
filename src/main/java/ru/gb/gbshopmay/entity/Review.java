package ru.gb.gbshopmay.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.gbshopmay.entity.common.BaseEntity;
import ru.gb.gbshopmay.entity.security.AccountUser;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountUser accountUser;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "comment")
    private String comment;
    @Column(name = "approved")
    private boolean approved;

    @Builder
    public Review(AccountUser accountUser, Product product, String comment, boolean approved) {
        this.accountUser = accountUser;
        this.product = product;
        this.comment = comment;
        this.approved = approved;
    }
}
