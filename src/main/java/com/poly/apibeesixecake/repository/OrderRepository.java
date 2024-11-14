package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByAccount_Idaccount(String idaccount);
    List<Order> findByIdstatus_Idstatus(Integer idstatus);
    List<Order> findByIdstatuspay_Idstatuspay(Integer idstatuspay);

}
