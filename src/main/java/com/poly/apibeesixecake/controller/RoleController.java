package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Role;
import com.poly.apibeesixecake.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{idrole}")
    public ResponseEntity<?> getRoleById(@PathVariable Integer idrole) {
        Role role = roleService.getRoleById(idrole);
        if (role != null) {
            return new ResponseEntity<>(role, HttpStatus.OK);
        }
        return new ResponseEntity<>("Vai trò không tồn tại.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        try {
            Role createdRole = roleService.createRole(role);
            return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idrole}")
    public ResponseEntity<?> updateRole(@PathVariable Integer idrole, @RequestBody Role roleDetails) {
        try {
            Role updatedRole = roleService.updateRole(idrole, roleDetails);
            if (updatedRole != null) {
                return new ResponseEntity<>(updatedRole, HttpStatus.OK);
            }
            return new ResponseEntity<>("Vai trò không tồn tại.", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idrole}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer idrole) {
        try {
            roleService.deleteRole(idrole);
            return new ResponseEntity<>("Xóa thành công.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
