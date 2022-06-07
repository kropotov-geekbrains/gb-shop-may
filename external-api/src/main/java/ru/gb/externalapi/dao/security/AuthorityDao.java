package ru.gb.externalapi.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.externalapi.entity.security.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
    
}
