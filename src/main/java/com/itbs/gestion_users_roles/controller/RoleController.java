package com.itbs.gestion_users_roles.controller;

import com.itbs.gestion_users_roles.dto.RoleDTO;
import com.itbs.gestion_users_roles.entity.Role;
import com.itbs.gestion_users_roles.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    private RoleDTO toDto(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .nom(role.getNom())
                .description(role.getDescription())
                .permissions(role.getPermissions())
                .build();
    }

    @GetMapping
    public List<RoleDTO> getAll() {
        return roleService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public RoleDTO getById(@PathVariable Long id) {
        return toDto(roleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO roleDto) {
        Role role = Role.builder()
                .nom(roleDto.getNom())
                .description(roleDto.getDescription())
                .permissions(roleDto.getPermissions())
                .build();
        Role created = roleService.create(role);
        return ResponseEntity.created(URI.create("/api/roles/" + created.getId())).body(toDto(created));
    }

    @PutMapping("/{id}")
    public RoleDTO update(@PathVariable Long id, @RequestBody RoleDTO roleDto) {
        Role role = Role.builder()
                .nom(roleDto.getNom())
                .description(roleDto.getDescription())
                .permissions(roleDto.getPermissions())
                .build();
        return toDto(roleService.update(id, role));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }

    @PostMapping("/{roleId}/permissions/{permId}")
    public RoleDTO addPermission(@PathVariable Long roleId, @PathVariable Long permId) {
        return toDto(roleService.addPermission(roleId, permId));
    }

    @DeleteMapping("/{roleId}/permissions/{permId}")
    public RoleDTO removePermission(@PathVariable Long roleId, @PathVariable Long permId) {
        return toDto(roleService.removePermission(roleId, permId));
    }
}
