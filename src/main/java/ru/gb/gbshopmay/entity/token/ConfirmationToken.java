package ru.gb.gbshopmay.entity.token;

import lombok.*;
import ru.gb.gbshopmay.entity.security.AccountUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "CONFIRMATION_TOKEN")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = AccountUser.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    private AccountUser accountUser;

    public ConfirmationToken(AccountUser accountUser) {
        this.accountUser = accountUser;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}
