package com.example.domain.primary.repository;


import com.example.domain.primary.entity.DtbAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<DtbAdmin, Long> {

    DtbAdmin findOneByUserName(String userName);

    DtbAdmin findOneById(Long id);

}
