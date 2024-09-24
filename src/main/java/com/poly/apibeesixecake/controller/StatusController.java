package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Status;
import com.poly.apibeesixecake.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatuses() {
        List<Status> statuses = statusService.getAllStatuses();
        return ResponseEntity.ok(statuses);
    }
    @GetMapping("/name/partial/{statusname}")
    public ResponseEntity<?> getStatusesByPartialName(@PathVariable String statusname) {
        List<Status> statuses = statusService.getStatusesByPartialName(statusname);
        if (statuses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Không tìm thấy trạng thái nào.");
            }});
        }
        return ResponseEntity.ok(statuses);
    }
    @GetMapping("/{idstatus}")
    public ResponseEntity<?> getStatusById(@PathVariable Integer idstatus) {
        Status status = statusService.getStatusById(idstatus);
        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Trạng thái không tồn tại.");
            }});
        }
        return ResponseEntity.ok(status);
    }

    @PostMapping
    public ResponseEntity<?> createStatus(@RequestBody Status status) {
        try {
            Status createdStatus = statusService.createStatus(status);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idstatus}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer idstatus, @RequestBody Status statusDetails) {
        try {
            Status updatedStatus = statusService.updateStatus(idstatus, statusDetails);
            if (updatedStatus == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                    put("error", "Trạng thái không tồn tại.");
                }});
            }
            return ResponseEntity.ok(updatedStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idstatus}")
    public ResponseEntity<?> deleteStatus(@PathVariable Integer idstatus) {
        try {
            statusService.deleteStatus(idstatus);
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
