package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CartItems")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcartitem;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "idproductdetail")
    private ProductDetail productdetail;
    @ManyToOne
    @JoinColumn(name = "idshoppingcart")
    private ShoppingCart shoppingcart;
}
