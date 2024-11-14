package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idorder;

    private LocalDateTime orderdate;
    private String addressdetail;
    private Float shipfee;
    private Float total;

    @ManyToOne
    @JoinColumn(name = "idaccount")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "iddiscount")
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "idpayment")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "idstatuspay")
    private Status idstatuspay;
}
