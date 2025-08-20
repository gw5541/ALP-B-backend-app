package com.kt.backendapp.dto;

import com.kt.backendapp.domain.entity.UserPreset;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 프리셋 DTO
 */
@Getter
@Setter
public class PresetDto {
    
    private Long presetId;
    private Long userId;
    private String name;
    private Map<String, Object> filters;
    private LocalDateTime createdAt;
    
    public PresetDto(UserPreset entity) {
        this.presetId = entity.getPresetId();
        this.userId = entity.getUserId();
        this.name = entity.getName();
        this.filters = entity.getFilters();
        this.createdAt = entity.getCreatedAt();
    }
}
