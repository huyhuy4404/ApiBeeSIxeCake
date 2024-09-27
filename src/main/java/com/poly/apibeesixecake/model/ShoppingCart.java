package com.poly.apibeesixecake.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ShoppingCarts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idshoppingcart;
    private String idaccount;
}
