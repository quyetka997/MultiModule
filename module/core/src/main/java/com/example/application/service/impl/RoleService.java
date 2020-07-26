package com.example.application.service.impl;

import com.example.application.service.IRoleService;
import com.example.domain.primary.dto.RoleDTO;
import com.example.domain.primary.entity.DtbRole;
import com.example.domain.primary.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public RoleDTO insertRole(RoleDTO roleDTO) {
        DtbRole role = roleDTO.toEntity(roleDTO, new DtbRole());
        return roleDTO.toDTO(roleRepository.save(role));
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) {
        DtbRole role = roleRepository.findOneById(roleDTO.id);
        return roleDTO.toDTO(roleRepository.save(role));
    }

    @Override
    public DtbRole findOneByRoleName(String roleName) {
        return roleRepository.findOneByRoleName(roleName);
    }

    @Override
    public RoleDTO getRole(Long id) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO = roleDTO.toDTO(roleRepository.findOneById(id));
        return roleDTO;
    }

    @Override
    public boolean isExist(String role) {
        return roleRepository.findOneByRoleName(role) != null;
    }

    @Override
    public void removeRole(Long id) {
        roleRepository.delete(roleRepository.findOneById(id));
    }
}
