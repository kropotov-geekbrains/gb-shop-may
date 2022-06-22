package ru.gb.gbshopmay.entity.security;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ACCOUNT_ROLE")
public class AccountRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    Set<AccountUser> roles;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name="role_authority",
            joinColumns = {
                @JoinColumn(name = "role_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name="authority_id", referencedColumnName = "id")
            }
    )
    private Set<Authority> authorities;

}
