package com.kt.backendapp.dto;

import com.kt.backendapp.domain.entity.UserFavoriteDistrict;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 관심 지역 DTO
 */
@Getter
@Setter
public class FavoriteDto {
    
    private Long userId;
    private Long districtId;
    private String districtName;
    private LocalDateTime createdAt;
    
    public FavoriteDto(UserFavoriteDistrict entity) {
        this.userId = entity.getUserId();
        this.districtId = entity.getDistrictId();
        this.districtName = entity.getDistrict() != null ? entity.getDistrict().getName() : null;
        this.createdAt = entity.getCreatedAt();
    }
}
