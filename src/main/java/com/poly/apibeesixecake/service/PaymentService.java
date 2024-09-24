package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Payment;
import com.poly.apibeesixecake.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Integer idpayment) {
        return paymentRepository.findById(idpayment).orElse(null);
    }

    public Payment createPayment(Payment payment) {
        if (paymentNameExists(payment.getPaymentname())) {
            throw new IllegalArgumentException("Tên phương thức thanh toán đã tồn tại.");
        }
        return paymentRepository.save(payment);
    }
    public Payment updatePayment(Integer idpayment, Payment paymentDetails) {
        Payment existingPayment = paymentRepository.findById(idpayment).orElse(null);
        if (existingPayment != null) {
            if (paymentNameExists(paymentDetails.getPaymentname()) &&
                    !paymentDetails.getPaymentname().equalsIgnoreCase(existingPayment.getPaymentname())) {
                throw new IllegalArgumentException("Tên phương thức thanh toán đã tồn tại.");
            }
            existingPayment.setPaymentname(paymentDetails.getPaymentname());
            return paymentRepository.save(existingPayment);
        }
        return null;
    }

    public void deletePayment(Integer idpayment) {
        if (!paymentRepository.existsById(idpayment)) {
            throw new IllegalArgumentException("Phương thức thanh toán không tồn tại.");
        }
        paymentRepository.deleteById(idpayment);
    }

    private boolean paymentNameExists(String paymentName) {
        return paymentRepository.findAll().stream()
                .anyMatch(p -> p.getPaymentname().equalsIgnoreCase(paymentName));
    }
}
