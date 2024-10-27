package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.OrderStatusHistory;
import com.poly.apibeesixecake.service.OrderStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/order-status-history")
public class OrderStatusHistoryController {

    @Autowired
    private OrderStatusHistoryService orderStatusHistoryService;

    @GetMapping
    public ResponseEntity<List<OrderStatusHistory>> getAllOrderStatusHistories() {
        List<OrderStatusHistory> histories = orderStatusHistoryService.getAllOrderStatusHistories();
        return ResponseEntity.ok(histories);
    }

    @GetMapping("/{idorderstatushistory}")
    public ResponseEntity<?> getOrderStatusHistoryById(@PathVariable Integer idorderstatushistory) {
        try {
            OrderStatusHistory history = orderStatusHistoryService.getOrderStatusHistoryById(idorderstatushistory);
            return ResponseEntity.ok(history);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrderStatusHistory(@RequestBody OrderStatusHistory orderStatusHistory) {
        try {
            OrderStatusHistory createdHistory = orderStatusHistoryService.createOrderStatusHistory(orderStatusHistory);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHistory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idorderstatushistory}")
    public ResponseEntity<?> updateOrderStatusHistory(@PathVariable Integer idorderstatushistory, @RequestBody OrderStatusHistory orderStatusHistoryDetails) {
        try {
            OrderStatusHistory updatedHistory = orderStatusHistoryService.updateOrderStatusHistory(idorderstatushistory, orderStatusHistoryDetails);
            return ResponseEntity.ok(updatedHistory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idorderstatushistory}")
    public ResponseEntity<?> deleteOrderStatusHistory(@PathVariable Integer idorderstatushistory) {
        try {
            orderStatusHistoryService.deleteOrderStatusHistory(idorderstatushistory);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa lịch sử trạng thái thành công.");
            }});
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @GetMapping("/by-order/{idorder}")
    public ResponseEntity<List<OrderStatusHistory>> findByOrderId(@PathVariable Integer idorder) {
        List<OrderStatusHistory> histories = orderStatusHistoryService.findByOrderId(idorder);
        return ResponseEntity.ok(histories);
    }

    @GetMapping("/by-status/{idstatus}")
    public ResponseEntity<List<OrderStatusHistory>> findByStatusId(@PathVariable Integer idstatus) {
        List<OrderStatusHistory> histories = orderStatusHistoryService.findByStatusId(idstatus);
        return ResponseEntity.ok(histories);
    }

    @GetMapping("/by-order-and-status")
    public ResponseEntity<List<OrderStatusHistory>> findByOrderIdAndStatusId(@RequestParam Integer idorder, @RequestParam Integer idstatus) {
        List<OrderStatusHistory> histories = orderStatusHistoryService.findByOrderIdAndStatusId(idorder, idstatus);
        return ResponseEntity.ok(histories);
    }
}
