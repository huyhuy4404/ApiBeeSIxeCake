package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProduct_Idproduct(Integer idproduct);
    List<Review> findByAccount_Idaccount(String idaccount);
}
