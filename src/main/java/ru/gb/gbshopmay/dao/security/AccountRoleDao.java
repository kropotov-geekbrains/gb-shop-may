package ru.gb.gbshopmay.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.security.AccountRole;

public interface AccountRoleDao extends JpaRepository<AccountRole, Long> {
    AccountRole findByName(String role_user);
}
