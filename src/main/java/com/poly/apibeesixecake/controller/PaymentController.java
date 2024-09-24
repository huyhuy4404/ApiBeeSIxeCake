package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Payment;
import com.poly.apibeesixecake.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{idpayment}")
    public ResponseEntity<?> getPaymentById(@PathVariable Integer idpayment) {
        Payment payment = paymentService.getPaymentById(idpayment);
        if (payment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Phương thức thanh toán không tồn tại.");
            }});
        }
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        try {
            Payment createdPayment = paymentService.createPayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idpayment}")
    public ResponseEntity<?> updatePayment(@PathVariable Integer idpayment, @RequestBody Payment paymentDetails) {
        try {
            Payment updatedPayment = paymentService.updatePayment(idpayment, paymentDetails);
            if (updatedPayment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                    put("error", "Phương thức thanh toán không tồn tại.");
                }});
            }
            return ResponseEntity.ok(updatedPayment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
    @DeleteMapping("/{idpayment}")
    public ResponseEntity<?> deletePayment(@PathVariable Integer idpayment) {
        try {
            paymentService.deletePayment(idpayment);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa thành công.");
            }});
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
}
