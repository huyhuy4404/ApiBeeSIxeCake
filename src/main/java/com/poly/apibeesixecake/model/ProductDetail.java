package com.poly.apibeesixecake.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ProductDetails")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproductdetail;
    private Float unitprice;
    private Integer quantityinstock;
    private LocalDateTime manufacturedate;
    private LocalDateTime expirationdate;
    @ManyToOne
    @JoinColumn(name = "idproduct")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "idsize")
    private Size size;
}
