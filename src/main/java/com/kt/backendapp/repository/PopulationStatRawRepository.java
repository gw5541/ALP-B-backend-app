package com.kt.backendapp.repository;

import com.kt.backendapp.domain.entity.PopulationStatRaw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 인구 통계 원본 Repository
 */
@Repository
public interface PopulationStatRawRepository extends JpaRepository<PopulationStatRaw, PopulationStatRaw.PopulationStatRawId> {
    
    /**
     * 기간별 페이지 조회
     */
    @Query("SELECT p FROM PopulationStatRaw p WHERE p.districtId = :districtId " +
           "AND p.stdrDeId BETWEEN :from AND :to " +
           "AND (:hourFrom IS NULL OR p.tmzonPdSe >= :hourFrom) " +
           "AND (:hourTo IS NULL OR p.tmzonPdSe <= :hourTo) " +
           "ORDER BY p.stdrDeId DESC, p.tmzonPdSe ASC")
    Page<PopulationStatRaw> findByConditions(
        @Param("districtId") Long districtId,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to,
        @Param("hourFrom") Long hourFrom,
        @Param("hourTo") Long hourTo,
        Pageable pageable
    );
    
    /**
     * 특정 날짜의 시간별 데이터 조회
     */
    @Query("SELECT p FROM PopulationStatRaw p WHERE p.districtId = :districtId " +
           "AND p.stdrDeId = :date ORDER BY p.tmzonPdSe ASC")
    List<PopulationStatRaw> findByDistrictIdAndDate(
        @Param("districtId") Long districtId,
        @Param("date") LocalDate date
    );
    
    /**
     * 기간별 평균 계산을 위한 조회
     */
    @Query("SELECT p FROM PopulationStatRaw p WHERE p.districtId = :districtId " +
           "AND p.stdrDeId BETWEEN :from AND :to")
    List<PopulationStatRaw> findByDistrictIdAndDateRange(
        @Param("districtId") Long districtId,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to
    );
}
