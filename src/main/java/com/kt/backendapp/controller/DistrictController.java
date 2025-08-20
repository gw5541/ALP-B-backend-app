package com.kt.backendapp.controller;

import com.kt.backendapp.domain.entity.District;
import com.kt.backendapp.service.DistrictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 자치구 컨트롤러
 */
@Tag(name = "Districts", description = "자치구 API")
@RestController
@RequestMapping("/api/v1/districts")
@RequiredArgsConstructor
public class DistrictController {
    
    private final DistrictService districtService;
    
    @Operation(summary = "자치구 목록 조회", description = "모든 자치구 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<List<District>> getDistricts() {
        List<District> districts = districtService.getAllDistricts();
        return ResponseEntity.ok(districts);
    }
}
