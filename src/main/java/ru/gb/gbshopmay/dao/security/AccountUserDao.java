package ru.gb.gbshopmay.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.security.AccountUser;

import java.util.Optional;

public interface AccountUserDao extends JpaRepository<AccountUser, Long> {

    Optional<AccountUser> findByUsername(String name);
}
