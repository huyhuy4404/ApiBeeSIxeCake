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
    @GetMapping("/productdetail/{idproductdetail}")
    public ResponseEntity<?> findByProductDetailId(@PathVariable Integer idproductdetail) {
        try {
            List<CartItem> cartItems = cartItemService.findByProductDetailId(idproductdetail);
            if (cartItems.isEmpty()) {
                // Trường hợp không tìm thấy mặt hàng, trả về lỗi 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                    put("error", "Không tìm thấy mặt hàng với idproductdetail này.");
                }});
            }
            // Trả về danh sách cartItems nếu tìm thấy mặt hàng
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            // Xử lý lỗi bất kỳ xảy ra trong quá trình thực hiện
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<String, String>() {{
                put("error", "Đã xảy ra lỗi trong quá trình xử lý.");
            }});
        }
    }

    @DeleteMapping("/productdetail/{idproductdetail}")
    public ResponseEntity<?> deleteByProductDetailId(@PathVariable Integer idproductdetail) {
        try {
            cartItemService.deleteByProductDetailId(idproductdetail);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa mặt hàng có idproductdetail thành công.");
            }});
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
}
