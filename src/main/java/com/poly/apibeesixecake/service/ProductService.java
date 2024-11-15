package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Product;
import com.poly.apibeesixecake.model.Category;
import com.poly.apibeesixecake.repository.ProductRepository;
import com.poly.apibeesixecake.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private boolean productNameExists(String productName) {
        return productRepository.findAll().stream()
                .anyMatch(p -> p.getProductname().equalsIgnoreCase(productName));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer idproduct) {
        return productRepository.findById(idproduct).orElse(null);
    }

    public Product createProduct(Product product) {
        Integer categoryId = product.getCategory().getIdcategory();
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new IllegalArgumentException("Loại sản phẩm không tồn tại.");
        }

        if (productNameExists(product.getProductname())) {
            throw new IllegalArgumentException("Tên sản phẩm đã tồn tại.");
        }

        product.setCategory(category.get());
        return productRepository.save(product);
    }

    public Product updateProduct(Integer idproduct, Product productDetails) {
        Product product = productRepository.findById(idproduct).orElse(null);
        if (product != null) {
            if (productNameExists(productDetails.getProductname()) &&
                    !productDetails.getProductname().equalsIgnoreCase(product.getProductname())) {
                throw new IllegalArgumentException("Tên sản phẩm đã tồn tại.");
            }

            product.setProductname(productDetails.getProductname());
            product.setImg(productDetails.getImg());
            product.setDescription(productDetails.getDescription());
            product.setIsactive(productDetails.getIsactive());
            product.setFavorite(productDetails.getFavorite());

            Integer categoryId = productDetails.getCategory().getIdcategory();
            Optional<Category> category = categoryRepository.findById(categoryId);
            if (category.isPresent()) {
                product.setCategory(category.get());
            } else {
                throw new IllegalArgumentException("Danh mục không tồn tại.");
            }

            return productRepository.save(product);
        }
        throw new IllegalArgumentException("Sản phẩm không tồn tại.");
    }

    public void deleteProduct(Integer idproduct) {
        if (!productRepository.existsById(idproduct)) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại.");
        }
        productRepository.deleteById(idproduct);
    }
}
