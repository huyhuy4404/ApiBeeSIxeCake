package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "OrderStatusHistory")
@Data
public class OrderStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idstatushistory;

    @ManyToOne
    @JoinColumn(name = "idorder")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "idstatus")
    private Status status;

    private LocalDateTime timestamp;

}
