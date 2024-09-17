package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.ProductDetail;
import com.poly.apibeesixecake.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productdetail")
public class ProductDetailController {

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping
    public ResponseEntity<?> getAllProductDetails() {
        List<ProductDetail> productDetails = productDetailService.getAllProductDetails();
        return new ResponseEntity<>(productDetails, HttpStatus.OK);
    }

    @GetMapping("/{idproductdetail}")
    public ResponseEntity<?> getProductDetailById(@PathVariable Integer idproductdetail) {
        ProductDetail productDetail = productDetailService.getProductDetailById(idproductdetail);
        if (productDetail != null) {
            return new ResponseEntity<>(productDetail, HttpStatus.OK);
        }
        return new ResponseEntity<>("Chi tiết sản phẩm không tồn tại.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createProductDetail(@RequestBody ProductDetail productDetail) {
        try {
            ProductDetail createdProductDetail = productDetailService.createProductDetail(productDetail);
            return new ResponseEntity<>(createdProductDetail, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idproductdetail}")
    public ResponseEntity<?> updateProductDetail(@PathVariable Integer idproductdetail, @RequestBody ProductDetail productDetailDetails) {
        try {
            ProductDetail updatedProductDetail = productDetailService.updateProductDetail(idproductdetail, productDetailDetails);
            if (updatedProductDetail != null) {
                return new ResponseEntity<>(updatedProductDetail, HttpStatus.OK);
            }
            return new ResponseEntity<>("Chi tiết sản phẩm không tồn tại.", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idproductdetail}")
    public ResponseEntity<?> deleteProductDetail(@PathVariable Integer idproductdetail) {
        try {
            productDetailService.deleteProductDetail(idproductdetail);
            return new ResponseEntity<>("Xóa thành công.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
