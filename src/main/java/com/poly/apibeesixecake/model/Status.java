package com.poly.apibeesixecake.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Statuses")
@Data
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idstatus;
    private String statusname;
}
