package com.kt.backendapp.service;

import com.kt.backendapp.domain.entity.UserFavoriteDistrict;
import com.kt.backendapp.dto.FavoriteCreateRequest;
import com.kt.backendapp.dto.FavoriteDto;
import com.kt.backendapp.exception.ConflictException;
import com.kt.backendapp.exception.NotFoundException;
import com.kt.backendapp.repository.UserAccountRepository;
import com.kt.backendapp.repository.UserFavoriteDistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 관심 지역 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {
    
    private final UserFavoriteDistrictRepository favoriteRepository;
    private final UserAccountRepository userRepository;
    private final DistrictService districtService;
    
    /**
     * 사용자별 관심 지역 목록 조회
     */
    public List<FavoriteDto> getFavorites(Long userId) {
        // 사용자 존재 확인
        validateUser(userId);
        
        List<UserFavoriteDistrict> favorites = favoriteRepository.findByUserIdWithDistrict(userId);
        return favorites.stream()
                .map(FavoriteDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * 관심 지역 추가
     */
    @Transactional
    public FavoriteDto addFavorite(Long userId, FavoriteCreateRequest request) {
        // 사용자 및 자치구 존재 확인
        validateUser(userId);
        districtService.getDistrictById(request.getDistrictId());
        
        // 중복 확인
        if (favoriteRepository.existsByUserIdAndDistrictId(userId, request.getDistrictId())) {
            throw new ConflictException("이미 관심 지역으로 등록된 지역입니다");
        }
        
        UserFavoriteDistrict favorite = new UserFavoriteDistrict();
        favorite.setUserId(userId);
        favorite.setDistrictId(request.getDistrictId());
        
        UserFavoriteDistrict saved = favoriteRepository.save(favorite);
        
        // 저장 후 조회하여 District 정보 포함
        UserFavoriteDistrict result = favoriteRepository.findByUserIdWithDistrict(userId).stream()
                .filter(f -> f.getDistrictId().equals(request.getDistrictId()))
                .findFirst()
                .orElse(saved);
        
        return new FavoriteDto(result);
    }
    
    /**
     * 관심 지역 삭제
     */
    @Transactional
    public void removeFavorite(Long userId, Long districtId) {
        // 사용자 존재 확인
        validateUser(userId);
        
        UserFavoriteDistrict.UserFavoriteDistrictId id = 
                new UserFavoriteDistrict.UserFavoriteDistrictId(userId, districtId);
        
        if (!favoriteRepository.existsById(id)) {
            throw new NotFoundException("관심 지역을 찾을 수 없습니다");
        }
        
        favoriteRepository.deleteById(id);
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
