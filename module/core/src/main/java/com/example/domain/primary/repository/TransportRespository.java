package com.example.domain.primary.repository;

import com.example.domain.primary.entity.DtbTransportRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportRespository extends JpaRepository<DtbTransportRequest, Long> {
}
