package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.CartItem;
import com.poly.apibeesixecake.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/cartitems")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        List<CartItem> cartItems = cartItemService.getAllCartItems();
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/{idcartitem}")
    public ResponseEntity<?> getCartItemById(@PathVariable Integer idcartitem) {
        CartItem cartItem = cartItemService.getCartItemById(idcartitem);
        if (cartItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Mặt hàng trong giỏ hàng không tồn tại.");
            }});
        }
        return ResponseEntity.ok(cartItem);
    }

    @PostMapping
    public ResponseEntity<?> createCartItem(@RequestBody CartItem cartItem) {
        try {
            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCartItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idcartitem}")
    public ResponseEntity<?> updateCartItem(@PathVariable Integer idcartitem, @RequestBody CartItem cartItemDetails) {
        try {
            CartItem updatedCartItem = cartItemService.updateCartItem(idcartitem, cartItemDetails);
            if (updatedCartItem == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                    put("error", "Mặt hàng trong giỏ hàng không tồn tại.");
                }});
            }
            return ResponseEntity.ok(updatedCartItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idcartitem}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Integer idcartitem) {
        CartItem cartItem = cartItemService.getCartItemById(idcartitem);
        if (cartItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Mặt hàng trong giỏ hàng không tồn tại.");
            }});
        }
        cartItemService.deleteCartItem(idcartitem);
        return ResponseEntity.ok(new HashMap<String, String>() {{
            put("message", "Xóa mặt hàng trong giỏ hàng thành công.");
        }});
    }

    @GetMapping("/shoppingcart/{idshoppingcart}")
    public ResponseEntity<List<CartItem>> findByShoppingCartId(@PathVariable Integer idshoppingcart) {
        List<CartItem> cartItems = cartItemService.findByShoppingCartId(idshoppingcart);
        return ResponseEntity.ok(cartItems);
    }
}
