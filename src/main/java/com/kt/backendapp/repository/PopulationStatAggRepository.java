package com.kt.backendapp.repository;

import com.kt.backendapp.domain.entity.PopulationStatAgg;
import com.kt.backendapp.domain.type.PeriodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 인구 통계 집계 Repository
 */
@Repository
public interface PopulationStatAggRepository extends JpaRepository<PopulationStatAgg, PopulationStatAgg.PopulationStatAggId> {
    
    /**
     * 특정 조건의 집계 데이터 조회
     */
    @Query("SELECT p FROM PopulationStatAgg p JOIN FETCH p.district WHERE p.districtId = :districtId " +
           "AND p.periodType = :periodType " +
           "AND p.periodStartDate >= :from AND p.periodStartDate <= :to " +
           "ORDER BY p.periodStartDate ASC")
    List<PopulationStatAgg> findByConditions(
        @Param("districtId") Long districtId,
        @Param("periodType") PeriodType periodType,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to
    );
    
    /**
     * 특정 기간의 집계 데이터 조회
     */
    @Query("SELECT p FROM PopulationStatAgg p JOIN FETCH p.district WHERE p.districtId = :districtId " +
           "AND p.periodType = :periodType AND p.periodStartDate = :periodStartDate")
    Optional<PopulationStatAgg> findByDistrictIdAndPeriodTypeAndPeriodStartDate(
        @Param("districtId") Long districtId, 
        @Param("periodType") PeriodType periodType, 
        @Param("periodStartDate") LocalDate periodStartDate
    );
    
    /**
     * 최근 N개월 데이터 조회
     */
    @Query("SELECT p FROM PopulationStatAgg p JOIN FETCH p.district WHERE p.districtId = :districtId " +
           "AND p.periodType = 'MONTHLY' " +
           "AND p.periodStartDate >= :fromDate " +
           "ORDER BY p.periodStartDate DESC")
    List<PopulationStatAgg> findRecentMonthlyData(
        @Param("districtId") Long districtId,
        @Param("fromDate") LocalDate fromDate
    );
}
