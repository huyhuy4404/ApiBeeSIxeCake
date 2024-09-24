package com.poly.apibeesixecake.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Discounts")
@Data
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddiscount;
    private String discountcode;
    private Double discountpercentage;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
}
