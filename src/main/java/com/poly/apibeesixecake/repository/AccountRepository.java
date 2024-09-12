package com.poly.apibeesixecake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.poly.apibeesixecake.model.Account;

import java.util.List;
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByEmail(String email);

    List<Account> findByPhonenumber(String phonenumber);
}
