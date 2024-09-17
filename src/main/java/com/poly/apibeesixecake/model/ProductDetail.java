package com.poly.apibeesixecake.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ProductDetails")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idproductdetail;
    private Float unitprice;
    private int quantitystock;
    @ManyToOne
    @JoinColumn(name = "idproduct")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "idsize")
    private Size size;
}
