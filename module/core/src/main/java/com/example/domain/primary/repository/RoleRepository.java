package com.example.domain.primary.repository;


import com.example.domain.primary.entity.DtbRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<DtbRole, Long> {
    DtbRole findOneById(Long id);

    DtbRole findOneByRoleName(String roleName);
}
