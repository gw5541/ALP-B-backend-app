package com.kt.backendapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 프리셋 생성 요청 DTO
 */
@Getter
@Setter
public class PresetCreateRequest {
    
    @NotBlank(message = "프리셋 이름은 필수입니다")
    private String name;
    
    @NotNull(message = "필터 조건은 필수입니다")
    private Map<String, Object> filters;
}
