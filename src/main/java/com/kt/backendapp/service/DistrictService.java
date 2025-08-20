package com.kt.backendapp.service;

import com.kt.backendapp.domain.entity.District;
import com.kt.backendapp.exception.NotFoundException;
import com.kt.backendapp.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 자치구 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DistrictService {
    
    private final DistrictRepository districtRepository;
    
    /**
     * 모든 자치구 조회
     */
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }
    
    /**
     * 자치구 조회 (검증용)
     */
    public District getDistrictById(Long districtId) {
        return districtRepository.findById(districtId)
                .orElseThrow(() -> new NotFoundException("자치구를 찾을 수 없습니다: " + districtId));
    }
}
