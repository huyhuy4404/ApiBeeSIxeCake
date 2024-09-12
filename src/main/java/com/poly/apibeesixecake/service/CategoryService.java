package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Category;
import com.poly.apibeesixecake.model.Product;
import com.poly.apibeesixecake.repository.CategoryRepository;
import com.poly.apibeesixecake.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    private boolean categoryNameExists(String categoryName) {
        return categoryRepository.findAll().stream()
                .anyMatch(c -> c.getCategoryname().equalsIgnoreCase(categoryName));
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer idcategory) {
        return categoryRepository.findById(idcategory).orElse(null);
    }

    public Category createCategory(Category category) {
        if (categoryNameExists(category.getCategoryname())) {
            throw new IllegalArgumentException("Tên loại sản phẩm đã tồn tại.");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Integer idcategory, Category categoryDetails) {
        Category category = categoryRepository.findById(idcategory).orElse(null);
        if (category != null) {
            if (categoryNameExists(categoryDetails.getCategoryname()) &&
                    !categoryDetails.getCategoryname().equalsIgnoreCase(category.getCategoryname())) {
                throw new IllegalArgumentException("Tên loại sản phẩm đã tồn tại.");
            }
            category.setCategoryname(categoryDetails.getCategoryname());
            return categoryRepository.save(category);
        }
        return null;
    }

    public void deleteCategory(Integer idcategory) {
        if (!categoryRepository.existsById(idcategory)) {
            throw new IllegalArgumentException("Loại sản phẩm không tồn tại.");
        }
        categoryRepository.deleteById(idcategory);
    }
    public List<Product> getProductsByCategory(Integer idcategory) {
        Category category = categoryRepository.findById(idcategory).orElse(null);
        if (category != null) {
            return productRepository.findByCategory(category);
        } else {
            throw new IllegalArgumentException("Loại sản phẩm không tồn tại.");
        }
    }
}
