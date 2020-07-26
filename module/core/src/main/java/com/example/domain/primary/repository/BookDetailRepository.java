package com.example.domain.primary.repository;

import com.example.domain.primary.entity.DtbBookDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailRepository extends JpaRepository<DtbBookDetail, Long> {
}
