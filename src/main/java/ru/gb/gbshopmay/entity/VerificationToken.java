package ru.gb.gbshopmay.entity;

import lombok.*;
import ru.gb.gbshopmay.entity.security.AccountUser;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "VERIFICATION_TOKEN")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String token;
  
    @OneToOne(targetEntity = AccountUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private AccountUser user;
    @Column(name = "expiry_date")
    private Date expiryDate;
   
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}