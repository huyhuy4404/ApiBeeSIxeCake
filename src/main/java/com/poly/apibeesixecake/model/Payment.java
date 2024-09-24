package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpayment;
    private String paymentname;
}
