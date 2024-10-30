package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Account;
import com.poly.apibeesixecake.model.ShoppingCart;
import com.poly.apibeesixecake.repository.AccountRepository;
import com.poly.apibeesixecake.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart getShoppingCartById(Integer idshoppingcart) {
        return shoppingCartRepository.findById(idshoppingcart).orElse(null);
    }

    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        Account account = accountRepository.findById(shoppingCart.getAccount().getIdaccount()).orElse(null);
        if (account == null) {
            throw new IllegalArgumentException("Tài khoản không tồn tại.");
        }
        shoppingCart.setAccount(account);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart updateShoppingCart(Integer idshoppingcart, ShoppingCart shoppingCartDetails) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(idshoppingcart).orElse(null);
        if (shoppingCart != null) {
            Account account = accountRepository.findById(shoppingCartDetails.getAccount().getIdaccount()).orElse(null);
            if (account == null) {
                throw new IllegalArgumentException("Tài khoản không tồn tại.");
            }
            shoppingCart.setAccount(account);
            return shoppingCartRepository.save(shoppingCart);
        }
        return null;
    }

    public void deleteShoppingCart(Integer idshoppingcart) {
        shoppingCartRepository.deleteById(idshoppingcart);
    }

    public List<ShoppingCart> findByAccount_Idaccount(String idaccount) {
        Account account = accountRepository.findById(idaccount).orElse(null);
        if (account == null) {
            throw new IllegalArgumentException("Tài khoản không tồn tại.");
        }
        return shoppingCartRepository.findByAccount_Idaccount(idaccount);
    }
}
