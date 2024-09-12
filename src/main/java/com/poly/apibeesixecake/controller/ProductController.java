package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Product;
import com.poly.apibeesixecake.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{idproduct}")
    public ResponseEntity<?> getProductById(@PathVariable Integer idproduct) {
        Product product = productService.getProductById(idproduct);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>("Sản phẩm không tồn tại.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idproduct}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer idproduct, @RequestBody Product productDetails) {
        try {
            Product updatedProduct = productService.updateProduct(idproduct, productDetails);
            if (updatedProduct != null) {
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            }
            return new ResponseEntity<>("Sản phẩm không tồn tại.", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idproduct}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer idproduct) {
        try {
            productService.deleteProduct(idproduct);
            return new ResponseEntity<>("Xóa thành công.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
