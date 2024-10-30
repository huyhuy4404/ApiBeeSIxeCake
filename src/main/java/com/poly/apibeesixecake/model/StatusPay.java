package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "StatusPays")
public class StatusPay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idstatuspay;

    private String statuspayname;
}
