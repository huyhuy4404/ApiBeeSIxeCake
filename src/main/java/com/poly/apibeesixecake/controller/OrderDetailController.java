package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.OrderDetail;
import com.poly.apibeesixecake.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/orderdetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<List<OrderDetail>> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        return ResponseEntity.ok(orderDetails);
    }

    @GetMapping("/{idorderdetail}")
    public ResponseEntity<?> getOrderDetailById(@PathVariable Integer idorderdetail) {
        OrderDetail orderDetail = orderDetailService.getOrderDetailById(idorderdetail);
        if (orderDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Chi tiết đơn hàng không tồn tại.");
            }});
        }
        return ResponseEntity.ok(orderDetail);
    }

    @GetMapping("/order/{idorder}")
    public ResponseEntity<?> getOrderDetailsByOrderId(@PathVariable Integer idorder) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(idorder);
        if (orderDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Không có chi tiết đơn hàng cho đơn hàng này.");
            }});
        }
        return ResponseEntity.ok(orderDetails);
    }

    @PostMapping
    public ResponseEntity<?> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        try {
            OrderDetail createdOrderDetail = orderDetailService.createOrderDetail(orderDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idorderdetail}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable Integer idorderdetail, @RequestBody OrderDetail orderDetailDetails) {
        try {
            OrderDetail updatedOrderDetail = orderDetailService.updateOrderDetail(idorderdetail, orderDetailDetails);
            return ResponseEntity.ok(updatedOrderDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idorderdetail}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Integer idorderdetail) {
        try {
            orderDetailService.deleteOrderDetail(idorderdetail);
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
