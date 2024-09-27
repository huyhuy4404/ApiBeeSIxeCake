package com.poly.apibeesixecake.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idorderdetail;
    private Float unitprice;
    private Integer quantity;
    @Column(name = "Total", insertable = false, updatable = false)
    private float total;
    @ManyToOne
    @JoinColumn(name = "idorder")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "idproductdetail")
    private ProductDetail productdetail;
}
