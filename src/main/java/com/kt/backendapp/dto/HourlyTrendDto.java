package com.kt.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 시간별 트렌드 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HourlyTrendDto {
    
    private Long districtId;
    private String districtName;
    private LocalDate date;
    private List<HourlyData> currentData;
    private List<HourlyData> compareData; // lastWeek 비교 데이터 (옵션)
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HourlyData {
        private Long hour; // 0~23
        private Long total;
        private Map<String, Number> maleBuckets;
        private Map<String, Number> femaleBuckets;
    }
}
