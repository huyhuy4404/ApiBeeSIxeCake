package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.StatusPay;
import com.poly.apibeesixecake.service.StatusPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/statuspay")
public class StatusPayController {
    @Autowired
    private StatusPayService statusPayService;

    @GetMapping
    public ResponseEntity<List<StatusPay>> getAllStatusPays() {
        List<StatusPay> statusPays = statusPayService.getAllStatusPays();
        return ResponseEntity.ok(statusPays);
    }

    @GetMapping("/{idstatuspay}")
    public ResponseEntity<?> getStatusPayById(@PathVariable Integer idstatuspay) {
        StatusPay statusPay = statusPayService.getStatusPayById(idstatuspay);
        if (statusPay == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Trạng thái thanh toán không tồn tại.");
            }});
        }
        return ResponseEntity.ok(statusPay);
    }

    @PostMapping
    public ResponseEntity<?> createStatusPay(@RequestBody StatusPay statusPay) {
        try {
            StatusPay createdStatusPay = statusPayService.createStatusPay(statusPay);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStatusPay);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", "Không thể tạo trạng thái thanh toán. Vui lòng kiểm tra lại dữ liệu.");
            }});
        }
    }

    @PutMapping("/{idstatuspay}")
    public ResponseEntity<?> updateStatusPay(@PathVariable Integer idstatuspay, @RequestBody StatusPay statusPayDetails) {
        try {
            StatusPay updatedStatusPay = statusPayService.updateStatusPay(idstatuspay, statusPayDetails);
            if (updatedStatusPay == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                    put("error", "Trạng thái thanh toán không tồn tại.");
                }});
            }
            return ResponseEntity.ok(updatedStatusPay);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", "Không thể cập nhật trạng thái thanh toán. Vui lòng kiểm tra lại dữ liệu.");
            }});
        }
    }

    @DeleteMapping("/{idstatuspay}")
    public ResponseEntity<?> deleteStatusPay(@PathVariable Integer idstatuspay) {
        StatusPay statusPay = statusPayService.getStatusPayById(idstatuspay);
        if (statusPay == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Trạng thái thanh toán không tồn tại.");
            }});
        }
        statusPayService.deleteStatusPay(idstatuspay);
        return ResponseEntity.ok(new HashMap<String, String>() {{
            put("message", "Xóa trạng thái thanh toán thành công.");
        }});
    }
}
