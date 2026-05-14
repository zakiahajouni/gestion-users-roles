package com.itbs.gestion_users_roles.service;

import java.util.List;

import com.itbs.gestion_users_roles.entity.Permission;

public interface PermissionService {

    List<Permission> findAll();

    Permission findById(Long id);

    Permission create(Permission permission);

    Permission update(Long id, Permission permission);

    void delete(Long id);
}