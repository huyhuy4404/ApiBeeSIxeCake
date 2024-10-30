package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Favorites")
@Data
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idfavorite;

    @ManyToOne
    @JoinColumn(name = "idaccount")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "idproduct")
    private Product product;
}
