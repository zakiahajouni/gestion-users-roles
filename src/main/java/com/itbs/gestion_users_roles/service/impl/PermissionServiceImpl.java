package com.itbs.gestion_users_roles.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itbs.gestion_users_roles.entity.Permission;
import com.itbs.gestion_users_roles.repository.PermissionRepository;
import com.itbs.gestion_users_roles.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission findById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
    }

    @Override
    public Permission create(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(Long id, Permission permission) {
        Permission existing = findById(id);
        existing.setNom(permission.getNom());
        existing.setDescription(permission.getDescription());
        return permissionRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }
}