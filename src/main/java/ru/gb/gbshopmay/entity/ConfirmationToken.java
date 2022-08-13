//package ru.gb.gbshopmay.entity;
//
//import lombok.*;
//import ru.gb.gbshopmay.entity.security.AccountUser;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.UUID;
//
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Entity
//@Table(name = "CONFIRMATIONTOKEN")
//public class ConfirmationToken {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    private String confirmationToken;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date created_date;
//
//    @OneToOne(targetEntity = AccountUser.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "user_id")
//    private AccountUser accountUser;
//    @Column(name = "expiry_date")
//    private Date expiryDate;
//
//    public ConfirmationToken(AccountUser accountUser) {
//        this.accountUser = accountUser;
//        created_date = new Date();
//        confirmationToken = UUID.randomUUID().toString();
//    }
//}