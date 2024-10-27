package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    List<Favorite> findByAccount_Idaccount(String idaccount);
    List<Favorite> findByProduct_Idproduct(Integer idproduct);
}
