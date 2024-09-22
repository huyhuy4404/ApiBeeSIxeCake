package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Category;
import com.poly.apibeesixecake.model.Product;
import com.poly.apibeesixecake.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{idcategory}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer idcategory) {
        Category category = categoryService.getCategoryById(idcategory);
        if (category != null) {
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
            put("error", "Loại sản phẩm không tồn tại.");
        }});
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            Category createdCategory = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idcategory}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer idcategory, @RequestBody Category categoryDetails) {
        try {
            Category updatedCategory = categoryService.updateCategory(idcategory, categoryDetails);
            if (updatedCategory != null) {
                return ResponseEntity.ok(updatedCategory);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Loại sản phẩm không tồn tại.");
            }});
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idcategory}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer idcategory) {
        // Kiểm tra xem idcategory có tồn tại không
        if (categoryService.getCategoryById(idcategory) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Loại sản phẩm không tồn tại.");
            }});
        }

        try {
            categoryService.deleteCategory(idcategory);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa thành công.");
            }});
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", "Loại sản phẩm này không thể xóa vì đã có sản phẩm được tạo trong loại sản phẩm này");
            }});
        }
    }

    @GetMapping("/{idcategory}/products")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Integer idcategory) {
        try {
            List<Product> products = categoryService.getProductsByCategory(idcategory);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
}
