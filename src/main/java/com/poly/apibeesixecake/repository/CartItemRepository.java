package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByShoppingcart_Idshoppingcart(Integer idshoppingcart);
    List<CartItem> findByProductdetail_Idproductdetail(Integer idproductdetail);
}
