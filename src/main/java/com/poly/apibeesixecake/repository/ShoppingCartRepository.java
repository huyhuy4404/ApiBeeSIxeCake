package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    ShoppingCart findByIdaccount(String idaccount);
}
