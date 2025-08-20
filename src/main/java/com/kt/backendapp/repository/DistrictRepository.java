package com.kt.backendapp.repository;

import com.kt.backendapp.domain.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 자치구 Repository
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
}
