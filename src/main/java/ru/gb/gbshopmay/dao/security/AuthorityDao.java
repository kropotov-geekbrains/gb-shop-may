package ru.gb.gbshopmay.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.security.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
    
}
