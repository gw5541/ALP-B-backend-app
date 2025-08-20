package com.kt.backendapp.controller;

import com.kt.backendapp.dto.PresetCreateRequest;
import com.kt.backendapp.dto.PresetDto;
import com.kt.backendapp.dto.PresetUpdateRequest;
import com.kt.backendapp.service.PresetService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 프리셋 컨트롤러
 */
// @Tag(name = "Presets", description = "프리셋 API")
@RestController
@RequestMapping("/api/v1/users/{userId}/presets")
@RequiredArgsConstructor
public class PresetController {
    
    private final PresetService presetService;
    
    // @Operation(summary = "프리셋 목록 조회", description = "사용자의 프리셋 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<List<PresetDto>> getPresets(
            // @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId
    ) {
        List<PresetDto> presets = presetService.getPresets(userId);
        return ResponseEntity.ok(presets);
    }
    
    // @Operation(summary = "프리셋 생성", description = "새로운 프리셋을 생성합니다")
    @PostMapping
    public ResponseEntity<PresetDto> createPreset(
            // @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            
            @Valid @RequestBody PresetCreateRequest request
    ) {
        PresetDto preset = presetService.createPreset(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(preset);
    }
    
    // @Operation(summary = "프리셋 수정", description = "기존 프리셋을 수정합니다")
    @PutMapping("/{presetId}")
    public ResponseEntity<PresetDto> updatePreset(
            // @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            
            // @Parameter(description = "프리셋 ID", required = true)
            @PathVariable Long presetId,
            
            @RequestBody PresetUpdateRequest request
    ) {
        PresetDto preset = presetService.updatePreset(userId, presetId, request);
        return ResponseEntity.ok(preset);
    }
    
    // @Operation(summary = "프리셋 삭제", description = "프리셋을 삭제합니다")
    @DeleteMapping("/{presetId}")
    public ResponseEntity<Void> deletePreset(
            // @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            
            // @Parameter(description = "프리셋 ID", required = true)
            @PathVariable Long presetId
    ) {
        presetService.deletePreset(userId, presetId);
        return ResponseEntity.noContent().build();
    }
}
