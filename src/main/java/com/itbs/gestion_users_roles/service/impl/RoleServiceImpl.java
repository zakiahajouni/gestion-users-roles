package com.itbs.gestion_users_roles.service.impl;

import com.itbs.gestion_users_roles.entity.Permission;
import com.itbs.gestion_users_roles.entity.Role;
import com.itbs.gestion_users_roles.repository.RoleRepository;
import com.itbs.gestion_users_roles.repository.PermissionRepository;
import com.itbs.gestion_users_roles.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found: " + id));
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Long id, Role role) {
        Role existing = findById(id);
        existing.setNom(role.getNom());
        existing.setDescription(role.getDescription());
        existing.setPermissions(role.getPermissions());
        return roleRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Role existing = findById(id);
        roleRepository.delete(existing);
    }

    @Override
    public Role addPermission(Long roleId, Long permissionId) {
        Role role = findById(roleId);
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + permissionId));
        role.getPermissions().add(permission);
        return roleRepository.save(role);
    }

    @Override
    public Role removePermission(Long roleId, Long permissionId) {
        Role role = findById(roleId);
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + permissionId));
        role.getPermissions().remove(permission);
        return roleRepository.save(role);
    }
}
