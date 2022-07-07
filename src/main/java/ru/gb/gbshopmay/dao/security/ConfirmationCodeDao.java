package ru.gb.gbshopmay.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.security.ConfirmationCode;
public interface ConfirmationCodeDao extends JpaRepository<ConfirmationCode, Long> {
    ConfirmationCode findConfirmationCodeByAccountUser_Id (Long id);

}
