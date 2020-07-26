package com.example.domain.primary.repository;

import com.example.domain.primary.entity.DtbMasterType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterTypeRespository extends JpaRepository<DtbMasterType, Long> {
}
