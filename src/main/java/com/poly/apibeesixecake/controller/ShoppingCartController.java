package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.ShoppingCart;
import com.poly.apibeesixecake.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartService.getAllShoppingCarts();
        return ResponseEntity.ok(shoppingCarts);
    }

    @GetMapping("/{idshoppingcart}")
    public ResponseEntity<?> getShoppingCartById(@PathVariable Integer idshoppingcart) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartById(idshoppingcart);
        if (shoppingCart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Giỏ hàng không tồn tại.");
            }});
        }
        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping
    public ResponseEntity<?> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        try {
            ShoppingCart createdShoppingCart = shoppingCartService.createShoppingCart(shoppingCart);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdShoppingCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idshoppingcart}")
    public ResponseEntity<?> updateShoppingCart(@PathVariable Integer idshoppingcart, @RequestBody ShoppingCart shoppingCartDetails) {
        try {
            ShoppingCart updatedShoppingCart = shoppingCartService.updateShoppingCart(idshoppingcart, shoppingCartDetails);
            return ResponseEntity.ok(updatedShoppingCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idshoppingcart}")
    public ResponseEntity<?> deleteShoppingCart(@PathVariable Integer idshoppingcart) {
        try {
            shoppingCartService.deleteShoppingCart(idshoppingcart);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa giỏ hàng thành công.");
            }});
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
}
