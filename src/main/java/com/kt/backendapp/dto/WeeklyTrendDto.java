package com.kt.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 주별 트렌드 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyTrendDto {
    
    private Long districtId;
    private String districtName;
    private List<WeeklyData> weeklyData;
    
    /**
     * 주별 데이터
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeeklyData {
        private String weekPeriod; // "2025-W03" (ISO 주 형식)
        private Long totalAvg;
        private Map<String, Number> maleBucketsAvg;
        private Map<String, Number> femaleBucketsAvg;
    }
}