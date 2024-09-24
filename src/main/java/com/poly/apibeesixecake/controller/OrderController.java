package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Order;
import com.poly.apibeesixecake.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{idorder}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer idorder) {
        Order order = orderService.getOrderById(idorder);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Đơn hàng không tồn tại.");
            }});
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/account/{idaccount}")
    public ResponseEntity<List<Order>> getOrdersByAccountId(@PathVariable String idaccount) {
        List<Order> orders = orderService.getOrdersByAccountId(idaccount);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        if (order == null || order.getAccount() == null) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", "Thông tin đơn hàng không hợp lệ.");
            }});
        }
        try {
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idorder}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer idorder, @RequestBody Order orderDetails) {
        try {
            Order updatedOrder = orderService.updateOrder(idorder, orderDetails);
            if (updatedOrder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                    put("error", "Đơn hàng không tồn tại.");
                }});
            }
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idorder}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer idorder) {
        try {
            orderService.deleteOrder(idorder);
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
