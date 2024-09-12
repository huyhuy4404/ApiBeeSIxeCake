package com.poly.apibeesixecake.model;


import jakarta.persistence.*;

import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Categoryes")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcategory;
    private String categoryname;
}
