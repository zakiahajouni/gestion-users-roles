package com.itbs.gestion_users_roles.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itbs.gestion_users_roles.entity.Permission;
import com.itbs.gestion_users_roles.service.PermissionService;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public List<Permission> getAll() {
        return permissionService.findAll();
    }

    @GetMapping("/{id}")
    public Permission getById(@PathVariable Long id) {
        return permissionService.findById(id);
    }

    @PostMapping
    public Permission create(@RequestBody Permission permission) {
        return permissionService.create(permission);
    }

    @PutMapping("/{id}")
    public Permission update(@PathVariable Long id, @RequestBody Permission permission) {
        return permissionService.update(id, permission);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        permissionService.delete(id);
    }
}