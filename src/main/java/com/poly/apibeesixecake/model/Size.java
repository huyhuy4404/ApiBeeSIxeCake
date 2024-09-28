package com.poly.apibeesixecake.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Sizes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idsize;
    private String sizename;
}
