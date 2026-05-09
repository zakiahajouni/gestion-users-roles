package com.itbs.gestion_users_roles.dto;

import com.itbs.gestion_users_roles.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO {
    private Long id;
    private String nom;
    private String description;
    private Set<Permission> permissions;
}
