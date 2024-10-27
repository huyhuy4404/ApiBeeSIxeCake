package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Order;
import com.poly.apibeesixecake.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer idorder) {
        return orderRepository.findById(idorder).orElse(null);
    }

    public List<Order> getOrdersByAccountId(String idaccount) {
        return orderRepository.findByAccount_Idaccount(idaccount);
    }

    public Order createOrder(Order order) {
        if (order.getDiscount() != null && order.getDiscount().getIddiscount() == 0) {
            order.setDiscount(null);
        }
        validateOrderDependencies(order);
        return orderRepository.save(order);
    }

    public Order updateOrder(Integer idorder, Order orderDetails) {
        Order existingOrder = orderRepository.findById(idorder).orElse(null);
        if (existingOrder != null) {
            existingOrder.setOrderdate(orderDetails.getOrderdate());
            existingOrder.setTotalamount(orderDetails.getTotalamount());
            existingOrder.setAddressdetail(orderDetails.getAddressdetail());
            existingOrder.setShipfee(orderDetails.getShipfee());
            existingOrder.setAccount(orderDetails.getAccount());
            existingOrder.setStatus(orderDetails.getStatus());
            existingOrder.setAddress(orderDetails.getAddress());

            if (orderDetails.getDiscount() != null && orderDetails.getDiscount().getIddiscount() == 0) {
                existingOrder.setDiscount(null);
            } else {
                existingOrder.setDiscount(orderDetails.getDiscount());
            }

            existingOrder.setPayment(orderDetails.getPayment());
            validateOrderDependencies(existingOrder);
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    public void deleteOrder(Integer idorder) {
        if (!orderRepository.existsById(idorder)) {
            throw new IllegalArgumentException("Đơn hàng không tồn tại.");
        }
        orderRepository.deleteById(idorder);
    }

    private void validateOrderDependencies(Order order) {
        if (order.getAccount() == null || !accountRepository.existsById(order.getAccount().getIdaccount())) {
            throw new IllegalArgumentException("Tài khoản không tồn tại.");
        }
        if (order.getPayment() == null || !paymentRepository.existsById(order.getPayment().getIdpayment())) {
            throw new IllegalArgumentException("Phương thức thanh toán không tồn tại.");
        }
        if (order.getAddress() == null || !addressRepository.existsById(order.getAddress().getIdaddress())) {
            throw new IllegalArgumentException("Địa chỉ không tồn tại.");
        }
        if (order.getStatus() == null || !statusRepository.existsById(order.getStatus().getIdstatus())) {
            throw new IllegalArgumentException("Trạng thái không tồn tại.");
        }
    }
}
