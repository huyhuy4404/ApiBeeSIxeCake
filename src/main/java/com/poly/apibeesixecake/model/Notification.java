package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idnotification;

    @ManyToOne
    @JoinColumn(name = "idaccount")
    private Account account;

    private String notificationtext;

    private Boolean isread;

    private LocalDateTime createat;
}
