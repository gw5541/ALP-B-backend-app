package com.kt.backendapp.service;

import com.kt.backendapp.domain.entity.UserPreset;
import com.kt.backendapp.dto.PresetCreateRequest;
import com.kt.backendapp.dto.PresetDto;
import com.kt.backendapp.dto.PresetUpdateRequest;
import com.kt.backendapp.exception.NotFoundException;
import com.kt.backendapp.repository.UserAccountRepository;
import com.kt.backendapp.repository.UserPresetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 프리셋 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PresetService {
    
    private final UserPresetRepository presetRepository;
    private final UserAccountRepository userRepository;
    
    /**
     * 사용자별 프리셋 목록 조회
     */
    public List<PresetDto> getPresets(Long userId) {
        // 사용자 존재 확인
        validateUser(userId);
        
        List<UserPreset> presets = presetRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return presets.stream()
                .map(PresetDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * 프리셋 생성
     */
    @Transactional
    public PresetDto createPreset(Long userId, PresetCreateRequest request) {
        // 사용자 존재 확인
        validateUser(userId);
        
        UserPreset preset = new UserPreset();
        preset.setUserId(userId);
        preset.setName(request.getName());
        preset.setFilters(request.getFilters());
        
        UserPreset saved = presetRepository.save(preset);
        return new PresetDto(saved);
    }
    
    /**
     * 프리셋 수정
     */
    @Transactional
    public PresetDto updatePreset(Long userId, Long presetId, PresetUpdateRequest request) {
        // 사용자 존재 확인
        validateUser(userId);
        
        UserPreset preset = presetRepository.findByPresetIdAndUserId(presetId, userId)
                .orElseThrow(() -> new NotFoundException("프리셋을 찾을 수 없습니다"));
        
        if (request.getName() != null) {
            preset.setName(request.getName());
        }
        if (request.getFilters() != null) {
            preset.setFilters(request.getFilters());
        }
        
        UserPreset saved = presetRepository.save(preset);
        return new PresetDto(saved);
    }
    
    /**
     * 프리셋 삭제
     */
    @Transactional
    public void deletePreset(Long userId, Long presetId) {
        // 사용자 존재 확인
        validateUser(userId);
        
        UserPreset preset = presetRepository.findByPresetIdAndUserId(presetId, userId)
                .orElseThrow(() -> new NotFoundException("프리셋을 찾을 수 없습니다"));
        
        presetRepository.delete(preset);
    }
    
    /**
     * 사용자 존재 확인
     */
    private void validateUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("사용자를 찾을 수 없습니다: " + userId);
        }
    }
}
