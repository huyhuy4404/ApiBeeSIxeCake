package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.StatusPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPayRepository extends JpaRepository<StatusPay, Integer> {
}
