package ru.gb.gbshopmay.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.VerificationToken;

public interface VerificationTokenDao extends JpaRepository<VerificationToken, Long> {
    VerificationToken findVerificationTokenByToken(String token);
}
