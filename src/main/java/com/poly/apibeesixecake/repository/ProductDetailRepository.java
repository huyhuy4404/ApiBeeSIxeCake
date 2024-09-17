package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    List<ProductDetail> findByProduct_Idproduct(Integer idproduct);
}
