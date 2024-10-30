package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ShoppingCarts")
@Data
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idshoppingcart;

    @ManyToOne
    @JoinColumn(name = "idaccount")
    private Account account;
}
