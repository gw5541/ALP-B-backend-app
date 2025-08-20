package com.kt.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

/**
 * 연령 분포 (인구 피라미드) DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgeDistributionDto {
    
    private Long districtId;
    private String districtName;
    private LocalDate periodFrom;
    private LocalDate periodTo;
    private Map<String, Number> maleBucketsAvg;
    private Map<String, Number> femaleBucketsAvg;
    private Long totalAvg;
}
