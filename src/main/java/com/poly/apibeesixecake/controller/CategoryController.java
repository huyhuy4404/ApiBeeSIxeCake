package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Category;
import com.poly.apibeesixecake.model.Product;
import com.poly.apibeesixecake.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        List<Category> categories = categoryService.getAllCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{idcategory}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer idcategory) {
        Category category = categoryService.getCategoryById(idcategory);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
        return new ResponseEntity<>("Loại sản phẩm không tồn tại.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            Category createdCategory = categoryService.createCategory(category);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idcategory}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer idcategory, @RequestBody Category categoryDetails) {
        try {
            Category updatedCategory = categoryService.updateCategory(idcategory, categoryDetails);
            if (updatedCategory != null) {
                return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
            }
            return new ResponseEntity<>("Loại sản phẩm không tồn tại.", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idcategory}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer idcategory) {
        // Kiểm tra xem idcategory có tồn tại không
        if (categoryService.getCategoryById(idcategory) == null) {
            return new ResponseEntity<>("Loại sản phẩm không tồn tại.", HttpStatus.NOT_FOUND);
        }

        try {
            categoryService.deleteCategory(idcategory);
            return new ResponseEntity<>("Xóa thành công.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(" Loại sản phẩm này không thể xóa vì đã có sản phẩm được tạo trong loại sản phẩm này", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{idcategory}/products")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Integer idcategory) {
        try {
            List<Product> products = categoryService.getProductsByCategory(idcategory);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
