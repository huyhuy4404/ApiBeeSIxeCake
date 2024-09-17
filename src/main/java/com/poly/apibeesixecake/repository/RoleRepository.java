package com.poly.apibeesixecake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.poly.apibeesixecake.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
