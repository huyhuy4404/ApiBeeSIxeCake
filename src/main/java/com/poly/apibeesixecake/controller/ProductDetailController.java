package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.ProductDetail;
import com.poly.apibeesixecake.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/productdetail")
public class ProductDetailController {

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping
    public ResponseEntity<List<ProductDetail>> getAllProductDetails() {
        List<ProductDetail> productDetails = productDetailService.getAllProductDetails();
        return ResponseEntity.ok(productDetails);
    }

    @GetMapping("/{idproductdetail}")
    public ResponseEntity<?> getProductDetailById(@PathVariable Integer idproductdetail) {
        ProductDetail productDetail = productDetailService.getProductDetailById(idproductdetail);
        if (productDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Chi tiết sản phẩm không tồn tại.");
            }});
        }
        return ResponseEntity.ok(productDetail);
    }

    @GetMapping("/product/{idproduct}")
    public ResponseEntity<?> getProductDetailsByProductId(@PathVariable Integer idproduct) {
        List<ProductDetail> productDetails = productDetailService.getProductDetailsByProductId(idproduct);
        if (productDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Không có chi tiết sản phẩm cho sản phẩm này.");
            }});
        }
        return ResponseEntity.ok(productDetails);
    }

    @PostMapping
    public ResponseEntity<?> createProductDetail(@RequestBody ProductDetail productDetail) {
        try {
            ProductDetail createdProductDetail = productDetailService.createProductDetail(productDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idproductdetail}")
    public ResponseEntity<?> updateProductDetail(@PathVariable Integer idproductdetail, @RequestBody ProductDetail productDetailDetails) {
        try {
            ProductDetail updatedProductDetail = productDetailService.updateProductDetail(idproductdetail, productDetailDetails);
            if (updatedProductDetail == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                    put("error", "Chi tiết sản phẩm không tồn tại.");
                }});
            }
            return ResponseEntity.ok(updatedProductDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idproductdetail}")
    public ResponseEntity<?> deleteProductDetail(@PathVariable Integer idproductdetail) {
        try {
            productDetailService.deleteProductDetail(idproductdetail);
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
