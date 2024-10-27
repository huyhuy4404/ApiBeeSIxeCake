package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByAccount_Idaccount(String idaccount);
    List<Address> findByAccount_IdaccountAndIsDefaultTrue(String idaccount);
}
