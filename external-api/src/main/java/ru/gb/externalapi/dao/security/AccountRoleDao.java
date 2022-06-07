package ru.gb.externalapi.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.externalapi.entity.security.AccountRole;

public interface AccountRoleDao extends JpaRepository<AccountRole, Long> {
    AccountRole findByName(String role_user);
}
