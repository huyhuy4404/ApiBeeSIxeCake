package com.poly.apibeesixecake.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Accounts")
@Data
public class Account {
    @Id
    private String idaccount;
    private String password;
    private String fullname;
    private String email;
    private String phonenumber;
    private boolean active;
    private Integer idrole;

}
