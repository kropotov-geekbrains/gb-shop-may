package ru.gb.gbshopmay.entity.security;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.gb.gbshopmay.entity.common.InfoEntity;
import ru.gb.gbshopmay.entity.enums.AccountStatus;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNT_USER")
@EntityListeners(AuditingEntityListener.class)
public class AccountUser extends InfoEntity implements UserDetails {

    private String username;
    private String password;

    private String firstname;
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private Set<AccountRole> roles;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    @Override
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = roles.stream()
                .map(AccountRole::getAuthorities)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        authorities.addAll(roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList()));
        return authorities;
    }

    public AccountUser() {
    }

    @Builder
    public AccountUser(Long id, int version, String createdBy, LocalDateTime createdDate, String lastModifiedBy,
                       LocalDateTime lastModifiedDate, String username, String password, String firstname,
                       String lastname, String email, String phone, AccountStatus status, Set<AccountRole> roles,
                       boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        super(id, version, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.roles = roles;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }
}