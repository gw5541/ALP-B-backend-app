package com.kt.backendapp.dto;

import com.kt.backendapp.domain.entity.PopulationStatAgg;
import com.kt.backendapp.domain.type.PeriodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

/**
 * 인구 통계 집계 데이터 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class PopulationAggDto {
    
    private Long districtId;
    private String districtName;
    private PeriodType periodType;
    private LocalDate periodStartDate;
    private Long totalAvg;
    private Map<String, Number> maleBucketsAvg;
    private Map<String, Number> femaleBucketsAvg;
    private Long daytimeAvg;
    private Long nighttimeAvg;
    
    public PopulationAggDto(PopulationStatAgg entity) {
        this.districtId = entity.getDistrictId();
        this.districtName = entity.getDistrict() != null ? entity.getDistrict().getName() : null;
        this.periodType = entity.getPeriodType();
        this.periodStartDate = entity.getPeriodStartDate();
        this.totalAvg = entity.getTotAvg();
        this.maleBucketsAvg = entity.getMaleBucketsAvg();
        this.femaleBucketsAvg = entity.getFemaleBucketsAvg();
        this.daytimeAvg = entity.getDaytimeAvg();
        this.nighttimeAvg = entity.getNighttimeAvg();
    }
}
