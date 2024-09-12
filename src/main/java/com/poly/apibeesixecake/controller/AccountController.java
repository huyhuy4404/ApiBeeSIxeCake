package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Account;
import com.poly.apibeesixecake.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccount();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{idaccount}")
    public ResponseEntity<?> getAccountById(@PathVariable String idaccount) {
        Account account = accountService.getAccountById(idaccount);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        return new ResponseEntity<>("Tài khoản không tồn tại.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.createAccount(account);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request nếu có lỗi
        }
    }

    @PutMapping("/{idaccount}")
    public ResponseEntity<?> updateAccount(@PathVariable String idaccount, @RequestBody Account accountDetails) {
        try {
            Account updatedAccount = accountService.updateAccount(idaccount, accountDetails);
            if (updatedAccount != null) {
                return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
            }
            return new ResponseEntity<>("Tài khoản không tồn tại.", HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request nếu có lỗi
        }
    }
    @DeleteMapping("/{idaccount}")
    public ResponseEntity<?> deleteAccount(@PathVariable String idaccount) {
        try {
            accountService.deleteAccount(idaccount);
            return new ResponseEntity<>("Xóa thành công.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
