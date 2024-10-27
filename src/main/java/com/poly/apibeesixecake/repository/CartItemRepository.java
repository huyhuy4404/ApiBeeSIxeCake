package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByAccount_Idaccount(String idaccount);

    // Thêm phương thức tìm kiếm theo tài khoản và chi tiết sản phẩm
    List<CartItem> findByAccount_IdaccountAndProductdetail_Idproductdetail(String idaccount, Integer idproductdetail);

    // Thêm phương thức tìm kiếm tất cả các CartItem theo id sản phẩm
    List<CartItem> findByProductdetail_Idproductdetail(Integer idproductdetail);
}
