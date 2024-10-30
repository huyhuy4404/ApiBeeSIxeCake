package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    List<ShoppingCart> findByAccount_Idaccount(String idaccount);
}
