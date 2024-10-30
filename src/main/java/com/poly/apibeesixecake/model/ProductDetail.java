package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ProductDetails")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproductdetail;
    private Float unitprice;
    private Integer quantityinstock;

    @ManyToOne
    @JoinColumn(name = "idproduct")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "idsize")
    private Size size;
}
