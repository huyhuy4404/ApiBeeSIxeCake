package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Product;
import com.poly.apibeesixecake.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{idproduct}")
    public ResponseEntity<?> getProductById(@PathVariable Integer idproduct) {
        Product product = productService.getProductById(idproduct);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Sản phẩm không tồn tại.");
            }});
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idproduct}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer idproduct, @RequestBody Product productDetails) {
        try {
            Product updatedProduct = productService.updateProduct(idproduct, productDetails);
            if (updatedProduct == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                    put("error", "Sản phẩm không tồn tại.");
                }});
            }
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idproduct}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer idproduct) {
        try {
            productService.deleteProduct(idproduct);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa thành công.");
            }});
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
}
