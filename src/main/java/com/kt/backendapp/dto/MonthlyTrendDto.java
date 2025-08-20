package com.kt.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 월별 트렌드 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTrendDto {
    
    private Long districtId;
    private String districtName;
    private List<MonthlyData> monthlyData;
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyData {
        private String yearMonth; // "2025-07" 형식
        private Long totalAvg;
        private Map<String, Number> maleBucketsAvg;
        private Map<String, Number> femaleBucketsAvg;
    }
}
