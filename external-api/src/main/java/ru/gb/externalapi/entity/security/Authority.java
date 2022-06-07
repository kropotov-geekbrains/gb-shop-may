package ru.gb.externalapi.entity.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority {

    static final long serialVersionUID = 3258473332397639096L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String permission;

    @ManyToMany(mappedBy = "authorities")
    private Set<AccountRole> roles;

    @Override
    public String getAuthority() {
        return this.permission;
    }
}
