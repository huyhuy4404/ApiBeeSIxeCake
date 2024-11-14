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
    private StatusPayRepository statusPayRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private DiscountRepository discountRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer idorder) {
        return orderRepository.findById(idorder).orElse(null);
    }

    public List<Order> getOrdersByAccountId(String idaccount) {
        return orderRepository.findByAccount_Idaccount(idaccount);
    }
    public List<Order> getOrdersByStatusId(Integer idstatus) {
        return orderRepository.findByStatus_Idstatus(idstatus);
    }
    public List<Order> getOrdersByStatusPayId(Integer idstatuspay) {
        return orderRepository.findByStatusPay_Idstatuspay(idstatuspay);
    }

    public Order createOrder(Order order) {
        // Kiểm tra nếu discount không có id
        if (order.getDiscount() != null) {
            if (order.getDiscount().getIddiscount() == null) {
                throw new IllegalArgumentException("Mã giảm giá không tồn tại."); // Ném lỗi nếu iddiscount không tồn tại
            } else if (order.getDiscount().getIddiscount() == 0) {
                order.setDiscount(null); // Không lưu discount
            }
        } else {
            throw new IllegalArgumentException("Mã giảm giá không tồn tại.");
        }

        validateOrderDependencies(order);
        return orderRepository.save(order);
    }

    public Order updateOrder(Integer idorder, Order orderDetails) {
        Order existingOrder = orderRepository.findById(idorder).orElse(null);
        if (existingOrder != null) {
            existingOrder.setOrderdate(orderDetails.getOrderdate());
            existingOrder.setAddressdetail(orderDetails.getAddressdetail());
            existingOrder.setShipfee(orderDetails.getShipfee());
            existingOrder.setAccount(orderDetails.getAccount());
            existingOrder.setPayment(orderDetails.getPayment());
            existingOrder.setTotal(orderDetails.getTotal());
            existingOrder.setIdstatus(orderDetails.getIdstatus());
            existingOrder.setIdstatuspay(orderDetails.getIdstatuspay());

            // Kiểm tra nếu discount không có id
            if (orderDetails.getDiscount() != null) {
                if (orderDetails.getDiscount().getIddiscount() == null) {
                    throw new IllegalArgumentException("Mã giảm giá không tồn tại."); // Ném lỗi nếu iddiscount không tồn tại
                } else if (orderDetails.getDiscount().getIddiscount() == 0) {
                    existingOrder.setDiscount(null); // Không lưu discount
                } else {
                    existingOrder.setDiscount(orderDetails.getDiscount()); // Gán discount nếu id > 0
                }
            } else {
                throw new IllegalArgumentException("Mã giảm giá không tồn tại.");
            }

            existingOrder.setIdstatuspay(orderDetails.getIdstatuspay()); // Thêm logic cho idstatuspay

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
        }if (order.getIdstatuspay() == null || !statusPayRepository.existsById(order.getIdstatuspay().getIdstatuspay())) {
            throw new IllegalArgumentException("Trạng thái thanh toán không tồn tại.");
        }
        if (order.getDiscount() != null && !discountRepository.existsById(order.getDiscount().getIddiscount())) {
            throw new IllegalArgumentException("Mã giảm giá không tồn tại.");
        }if (order.getIdstatuspay() == null || !statusRepository.existsById(order.getIdstatus().getIdstatus())) {
            throw new IllegalArgumentException("Trạng thái đơn hàng không tồn tại.");
        }

    }
}
