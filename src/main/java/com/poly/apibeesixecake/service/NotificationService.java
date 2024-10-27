package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Notification;
import com.poly.apibeesixecake.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Integer idnotification) {
        return notificationRepository.findById(idnotification)
                .orElseThrow(() -> new IllegalArgumentException("Thông báo không tồn tại."));
    }

    public List<Notification> getNotificationsByAccountId(String idaccount) {
        return notificationRepository.findByAccount_Idaccount(idaccount);
    }

    public Notification createNotification(Notification notification) {
        notification.setCreateat(LocalDateTime.now()); // Set thời gian tạo thông báo
        return notificationRepository.save(notification);
    }

    public Notification updateNotification(Integer idnotification, Notification notificationDetails) {
        Notification notification = getNotificationById(idnotification);
        notification.setNotificationtext(notificationDetails.getNotificationtext());
        notification.setIsread(notificationDetails.getIsread());
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Integer idnotification) {
        Notification notification = getNotificationById(idnotification);
        notificationRepository.delete(notification);
    }
}
