package com.itbs.gestion_users_roles.service;

import com.itbs.gestion_users_roles.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findById(Long id);
    Role create(Role role);
    Role update(Long id, Role role);
    void delete(Long id);
    Role addPermission(Long roleId, Long permissionId);
    Role removePermission(Long roleId, Long permissionId);
}
