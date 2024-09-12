package com.poly.apibeesixecake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.poly.apibeesixecake.model.Size;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
//    Optional<Size> findBySizename(String sizename);
}
