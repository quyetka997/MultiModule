package com.example.application.service;


import com.example.domain.primary.dto.RoleDTO;
import com.example.domain.primary.entity.DtbRole;

public interface IRoleService {

    RoleDTO insertRole(RoleDTO roleDTO);

    RoleDTO updateRole(RoleDTO roleDTO);

    DtbRole findOneByRoleName(String roleName);

    RoleDTO getRole(Long id);

    boolean isExist(String role);

    void removeRole(Long id);
}
