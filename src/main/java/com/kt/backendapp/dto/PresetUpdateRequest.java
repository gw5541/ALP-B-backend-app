package com.kt.backendapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 프리셋 수정 요청 DTO
 */
@Getter
@Setter
public class PresetUpdateRequest {
    
    private String name;
    private Map<String, Object> filters;
}
