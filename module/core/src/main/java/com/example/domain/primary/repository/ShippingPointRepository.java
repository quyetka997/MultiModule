package com.example.domain.primary.repository;

import com.example.domain.primary.entity.DtbShippingPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingPointRepository extends JpaRepository<DtbShippingPoint, Long> {

}
