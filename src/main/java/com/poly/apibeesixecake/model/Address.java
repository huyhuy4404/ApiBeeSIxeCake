package com.poly.apibeesixecake.model;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "Addresses")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idaddress;
    private String housenumber;
    private String roadname;
    private String ward;
    private String district;
    private String city;
    private Boolean isDefault = false;
    @ManyToOne
    @JoinColumn(name = "idaccount")
    private Account account;
}
