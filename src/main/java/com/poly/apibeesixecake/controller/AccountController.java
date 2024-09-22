package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Account;
import com.poly.apibeesixecake.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccount();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{idaccount}")
    public ResponseEntity<?> getAccountById(@PathVariable String idaccount) {
        Account account = accountService.getAccountById(idaccount);
        if (account != null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
            put("error", "Tài khoản không tồn tại.");
        }});
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.createAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }}); // 400 Bad Request nếu có lỗi
        }
    }

    @PutMapping("/{idaccount}")
    public ResponseEntity<?> updateAccount(@PathVariable String idaccount, @RequestBody Account accountDetails) {
        try {
            Account updatedAccount = accountService.updateAccount(idaccount, accountDetails);
            if (updatedAccount != null) {
                return ResponseEntity.ok(updatedAccount);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Tài khoản không tồn tại.");
            }});
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }}); // 400 Bad Request nếu có lỗi
        }
    }

    @DeleteMapping("/{idaccount}")
    public ResponseEntity<?> deleteAccount(@PathVariable String idaccount) {
        try {
            accountService.deleteAccount(idaccount);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa thành công.");
            }});
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
}
