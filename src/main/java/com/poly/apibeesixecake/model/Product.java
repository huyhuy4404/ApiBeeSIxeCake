package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproduct;

    private String productname;

    private String img;

    private String description;

    private Boolean isactive;
    private Integer favorite;
    @ManyToOne
    @JoinColumn(name = "idcategory", nullable = false) // Thêm nullable = false nếu idcategory không được phép null
    private Category category;
}
