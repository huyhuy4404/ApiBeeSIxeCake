package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Role;
import com.poly.apibeesixecake.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Integer idrole) {
        return roleRepository.findById(idrole).orElse(null);
    }

    public Role createRole(Role role) {
        if (roleNameExists(role.getRolename())) {
            throw new IllegalArgumentException("Tên vai trò đã tồn tại.");
        }
        return roleRepository.save(role);
    }
    public Role updateRole(Integer idrole, Role roleDetails) {
        Role existingRole = roleRepository.findById(idrole).orElse(null);
        if (existingRole != null) {
            if (roleNameExists(roleDetails.getRolename()) &&
                    !roleDetails.getRolename().equalsIgnoreCase(existingRole.getRolename())) {
                throw new IllegalArgumentException("Tên vai trò đã tồn tại.");
            }
            existingRole.setRolename(roleDetails.getRolename());
            return roleRepository.save(existingRole);
        }
        return null;
    }

    public void deleteRole(Integer idrole) {
        if (!roleRepository.existsById(idrole)) {
            throw new IllegalArgumentException("Vai trò không tồn tại.");
        }
        roleRepository.deleteById(idrole);
    }

    private boolean roleNameExists(String roleName) {
        return roleRepository.findAll().stream()
                .anyMatch(r -> r.getRolename().equalsIgnoreCase(roleName));
    }
}
