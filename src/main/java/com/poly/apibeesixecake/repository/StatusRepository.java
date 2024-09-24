package com.poly.apibeesixecake.repository;

import com.poly.apibeesixecake.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    List<Status> findByStatusnameIgnoreCaseContaining(String statusname);
}
