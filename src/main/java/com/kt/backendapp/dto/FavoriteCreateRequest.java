package com.kt.backendapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 관심 지역 생성 요청 DTO
 */
@Getter
@Setter
public class FavoriteCreateRequest {
    
    @NotNull(message = "지역 ID는 필수입니다")
    private Long districtId;
}
