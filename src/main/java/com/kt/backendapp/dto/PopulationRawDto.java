package com.kt.backendapp.dto;

import com.kt.backendapp.domain.entity.PopulationStatRaw;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

/**
 * 인구 통계 원본 데이터 DTO
 */
@Getter
@Setter
public class PopulationRawDto {
    
    private Long districtId;
    private String districtName;
    private LocalDate date;
    private Long hour;
    private Long total;
    private Map<String, Number> maleBuckets;
    private Map<String, Number> femaleBuckets;
    
    public PopulationRawDto(PopulationStatRaw entity) {
        this.districtId = entity.getDistrictId();
        this.districtName = entity.getDistrict() != null ? entity.getDistrict().getName() : null;
        this.date = entity.getStdrDeId();
        this.hour = entity.getTmzonPdSe();
        this.total = entity.getTot();
        this.maleBuckets = entity.getMaleBuckets();
        this.femaleBuckets = entity.getFemaleBuckets();
    }
}
