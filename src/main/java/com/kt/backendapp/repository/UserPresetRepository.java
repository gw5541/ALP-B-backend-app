package com.kt.backendapp.repository;

import com.kt.backendapp.domain.entity.UserPreset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 프리셋 Repository
 */
@Repository
public interface UserPresetRepository extends JpaRepository<UserPreset, Long> {
    
    /**
     * 사용자별 프리셋 목록 조회
     */
    List<UserPreset> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    /**
     * 사용자별 특정 프리셋 조회
     */
    Optional<UserPreset> findByPresetIdAndUserId(Long presetId, Long userId);
}
