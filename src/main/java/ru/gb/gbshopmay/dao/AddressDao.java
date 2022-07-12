package ru.gb.gbshopmay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmay.entity.Address;

public interface AddressDao extends JpaRepository<Address, Long> {
}
