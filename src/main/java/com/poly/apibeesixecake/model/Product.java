package com.poly.apibeesixecake.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproduct;

    private String productname;

    private String img;

    private String description;

    private Boolean isactive;

    @ManyToOne
    @JoinColumn(name = "idcategory", nullable = false) // Thêm nullable = false nếu idcategory không được phép null
    private Category category;
}
