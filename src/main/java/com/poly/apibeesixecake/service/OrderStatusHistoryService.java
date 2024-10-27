package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.OrderStatusHistory;
import com.poly.apibeesixecake.repository.OrderStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusHistoryService {

    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    public List<OrderStatusHistory> getAllOrderStatusHistories() {
        return orderStatusHistoryRepository.findAll();
    }

    public OrderStatusHistory createOrderStatusHistory(OrderStatusHistory orderStatusHistory) {
        return orderStatusHistoryRepository.save(orderStatusHistory);
    }

    public OrderStatusHistory getOrderStatusHistoryById(Integer id) {
        return orderStatusHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lịch sử trạng thái với ID: " + id));
    }

    public OrderStatusHistory updateOrderStatusHistory(Integer id, OrderStatusHistory orderStatusHistory) {
        // Kiểm tra nếu lịch sử trạng thái tồn tại
        if (!orderStatusHistoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy lịch sử trạng thái với ID: " + id);
        }
        orderStatusHistory.setIdorderstatushistory(id);
        return orderStatusHistoryRepository.save(orderStatusHistory);
    }

    public void deleteOrderStatusHistory(Integer id) {
        // Kiểm tra nếu lịch sử trạng thái tồn tại trước khi xóa
        if (!orderStatusHistoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy lịch sử trạng thái với ID: " + id);
        }
        orderStatusHistoryRepository.deleteById(id);
    }

    public List<OrderStatusHistory> findByOrderId(Integer idorder) {
        return orderStatusHistoryRepository.findByOrder_Idorder(idorder);
    }

    public List<OrderStatusHistory> findByStatusId(Integer idstatus) {
        return orderStatusHistoryRepository.findByStatus_Idstatus(idstatus);
    }

    public List<OrderStatusHistory> findByOrderIdAndStatusId(Integer idorder, Integer idstatus) {
        return orderStatusHistoryRepository.findByOrder_IdorderAndStatus_Idstatus(idorder, idstatus);
    }
}
