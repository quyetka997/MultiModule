package com.example.domain.primary.repository;

import com.example.domain.primary.entity.DtbRole;
import com.example.domain.primary.entity.DtbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends JpaRepository<DtbUser, Long> {
    DtbUser findUserById(Long id);

    DtbUser findUserByUserName(String username);

    DtbUser findFirstByUserNameAndRolesIsContaining(String username, DtbRole role);
}
