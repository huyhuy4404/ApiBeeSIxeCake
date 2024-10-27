package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByAccount_Idaccount(String idaccount);
}
