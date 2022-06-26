package ru.gb.gbshopmay.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.token.ConfirmationToken;

public interface ConfirmationTokenDao extends JpaRepository<ConfirmationToken, Long> {

    ConfirmationToken findConfirmationToken(String token);
}

