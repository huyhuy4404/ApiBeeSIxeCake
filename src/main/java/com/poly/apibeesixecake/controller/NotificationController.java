package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Notification;
import com.poly.apibeesixecake.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{idnotification}")
    public ResponseEntity<?> getNotificationById(@PathVariable Integer idnotification) {
        try {
            Notification notification = notificationService.getNotificationById(idnotification);
            return ResponseEntity.ok(notification);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @GetMapping("/account/{idaccount}")
    public ResponseEntity<?> getNotificationsByAccountId(@PathVariable String idaccount) {
        List<Notification> notifications = notificationService.getNotificationsByAccountId(idaccount);
        if (notifications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Không có thông báo nào cho tài khoản này.");
            }});
        }
        return ResponseEntity.ok(notifications);
    }

    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody Notification notification) {
        try {
            Notification createdNotification = notificationService.createNotification(notification);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idnotification}")
    public ResponseEntity<?> updateNotification(@PathVariable Integer idnotification, @RequestBody Notification notificationDetails) {
        try {
            Notification updatedNotification = notificationService.updateNotification(idnotification, notificationDetails);
            return ResponseEntity.ok(updatedNotification);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idnotification}")
    public ResponseEntity<?> deleteNotification(@PathVariable Integer idnotification) {
        try {
            notificationService.deleteNotification(idnotification);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa thông báo thành công.");
            }});
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
}
