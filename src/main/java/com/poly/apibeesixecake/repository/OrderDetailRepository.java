package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrder_Idorder(Integer idorder);
}
