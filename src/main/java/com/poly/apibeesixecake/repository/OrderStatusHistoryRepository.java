package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Integer> {

    List<OrderStatusHistory> findByOrder_Idorder(Integer idorder);

    List<OrderStatusHistory> findByStatus_Idstatus(Integer idstatus);

    List<OrderStatusHistory> findByOrder_IdorderAndStatus_Idstatus(Integer idorder, Integer idstatus);
}
