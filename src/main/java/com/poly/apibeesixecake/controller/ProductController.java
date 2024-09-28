package com.poly.apibeesixecake.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.apibeesixecake.model.Product;
import com.poly.apibeesixecake.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Tên tệp không hợp lệ.");
        }

        String filePath = "D:/DATN/DATN/FEBeeSixCake/src/main/resources/templates/admin/images/" + fileName;
        File dest = new File(filePath);

        if (dest.exists()) {
            throw new IllegalArgumentException("Tệp đã tồn tại: " + fileName);
        }

        final long MAX_SIZE = 100 * 1024 * 1024; // 100MB
        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("Kích thước tệp vượt quá giới hạn cho phép.");
        }

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new IOException("Có lỗi xảy ra khi xử lý tệp hình ảnh: " + e.getMessage());
        }

        return fileName;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(
            @RequestParam("product") String productJson,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(productJson, Product.class);

            if (product == null) {
                return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                    put("error", "Sản phẩm không hợp lệ.");
                }});
            }

            product.setIsactive(file != null && !file.isEmpty() ? false : true);

            if (file != null && !file.isEmpty()) {
                String imageName = saveFile(file);
                product.setImg(imageName);
            }

            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<String, String>() {{
                put("error", "Có lỗi xảy ra khi xử lý tệp hình ảnh: " + e.getMessage());
            }});
        }
    }

    @PutMapping("/{idproduct}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer idproduct,
                                           @RequestParam("product") String productJson,
                                           @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(productJson, Product.class);

            if (product == null) {
                return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                    put("error", "Sản phẩm không hợp lệ.");
                }});
            }

            product.setIsactive(file != null && !file.isEmpty() ? false : true);

            if (file != null && !file.isEmpty()) {
                String imageName = saveFile(file);
                product.setImg(imageName);
            }

            Product updatedProduct = productService.updateProduct(idproduct, product);
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
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<String, String>() {{
                put("error", "Có lỗi xảy ra khi xử lý tệp hình ảnh: " + e.getMessage());
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
