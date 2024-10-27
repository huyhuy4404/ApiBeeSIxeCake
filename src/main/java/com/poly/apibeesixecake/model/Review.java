package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reviews")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idreview;
    private Integer rating;
    private String reviewtext;
    private LocalDateTime reviewdate;
    @ManyToOne
    @JoinColumn(name = "idaccount")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "idproduct")
    private Product product;
}
