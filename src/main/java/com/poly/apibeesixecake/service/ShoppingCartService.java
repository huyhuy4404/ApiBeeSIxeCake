package com.poly.apibeesixecake.service;

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
    private AccountRepository accountRepository; // Thêm accountRepository

    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart getShoppingCartById(Integer idshoppingcart) {
        return shoppingCartRepository.findById(idshoppingcart)
                .orElse(null);
    }

    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        if (!accountRepository.existsById(shoppingCart.getIdaccount())) {
            throw new IllegalArgumentException("Tài khoản không tồn tại.");
        }
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart updateShoppingCart(Integer idshoppingcart, ShoppingCart shoppingCartDetails) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(idshoppingcart)
                .orElseThrow(() -> new IllegalArgumentException("Giỏ hàng không tồn tại."));

        if (!accountRepository.existsById(shoppingCartDetails.getIdaccount())) {
            throw new IllegalArgumentException("Tài khoản không tồn tại.");
        }

        shoppingCart.setIdaccount(shoppingCartDetails.getIdaccount());
        return shoppingCartRepository.save(shoppingCart);
    }

    public void deleteShoppingCart(Integer idshoppingcart) {
        if (!shoppingCartRepository.existsById(idshoppingcart)) {
            throw new IllegalArgumentException("Giỏ hàng không tồn tại.");
        }
        shoppingCartRepository.deleteById(idshoppingcart);
    }
}
