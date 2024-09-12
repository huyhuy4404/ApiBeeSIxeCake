package com.poly.apibeesixecake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.poly.apibeesixecake.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
