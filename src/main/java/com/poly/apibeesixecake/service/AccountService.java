package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Account;
import com.poly.apibeesixecake.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class AccountService { // Đổi tên lớp thành AccountService cho nhất quán

    @Autowired
    private AccountRepository accountRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final int PHONE_NUMBER_LENGTH = 10;

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    public Account getAccountById(String idaccount) {
        return accountRepository.findById(idaccount).orElse(null);
    }

    public Account createAccount(Account account) {
        // Kiểm tra định dạng email
        if (!EMAIL_PATTERN.matcher(account.getEmail()).matches()) {
            throw new RuntimeException("Định dạng email không hợp lệ.");
        }

        // Kiểm tra số điện thoại
        if (account.getPhonenumber().length() != PHONE_NUMBER_LENGTH) {
            throw new RuntimeException("Số điện thoại phải có 10 chữ số.");
        }

        // Kiểm tra sự tồn tại của tài khoản, email và số điện thoại
        if (accountRepository.findById(account.getIdaccount()).isPresent()) {
            throw new RuntimeException("Tài khoản đã tồn tại.");
        }
        if (!accountRepository.findByEmail(account.getEmail()).isEmpty()) {
            throw new RuntimeException("Email đã được sử dụng.");
        }
        if (!accountRepository.findByPhonenumber(account.getPhonenumber()).isEmpty()) {
            throw new RuntimeException("Số điện thoại đã được sử dụng.");
        }

        account.setActive(true);

        return accountRepository.save(account);
    }

    public Account updateAccount(String idaccount, Account accountDetails) {
        // Kiểm tra xem tài khoản có tồn tại không
        Account account = accountRepository.findById(idaccount).orElse(null);
        if (account != null) {
            // Kiểm tra định dạng email
            if (!EMAIL_PATTERN.matcher(accountDetails.getEmail()).matches()) {
                throw new RuntimeException("Định dạng email không hợp lệ.");
            }

            // Kiểm tra số điện thoại
            if (accountDetails.getPhonenumber().length() != PHONE_NUMBER_LENGTH) {
                throw new RuntimeException("Số điện thoại phải có 10 chữ số.");
            }

            account.setPassword(accountDetails.getPassword());
            account.setFullname(accountDetails.getFullname());
            account.setEmail(accountDetails.getEmail());
            account.setPhonenumber(accountDetails.getPhonenumber());
            account.setActive(accountDetails.isActive());

            return accountRepository.save(account);
        }
        return null;
    }
    public void deleteAccount(String idaccount) {
        Account account = accountRepository.findById(idaccount).orElse(null);
        if (account == null) {
            throw new RuntimeException("Tài khoản không tồn tại.");
        }
        accountRepository.delete(account);
    }
}
