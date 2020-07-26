package com.example.domain.primary.dto;


import com.example.domain.primary.entity.DtbRole;

public class RoleDTO {

    public Long id;

    public String roleName;

    public RoleDTO toDTO(DtbRole role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.roleName = role.getRoleName();
        roleDTO.id = role.getId();
        return roleDTO;
    }

    public DtbRole toEntity(RoleDTO roleDTO,DtbRole role) {
        role.setRoleName(roleDTO.roleName);
        return role;
    }

}
