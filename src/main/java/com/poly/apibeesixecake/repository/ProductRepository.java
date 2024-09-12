package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.Category;
import com.poly.apibeesixecake.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);
}
