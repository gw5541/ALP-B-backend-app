package com.kt.backendapp.repository;

import com.kt.backendapp.domain.entity.UserFavoriteDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 사용자 관심 지역 Repository
 */
@Repository
public interface UserFavoriteDistrictRepository extends JpaRepository<UserFavoriteDistrict, UserFavoriteDistrict.UserFavoriteDistrictId> {
    
    /**
     * 사용자별 관심 지역 목록 조회
     */
    @Query("SELECT f FROM UserFavoriteDistrict f JOIN FETCH f.district WHERE f.userId = :userId ORDER BY f.createdAt DESC")
    List<UserFavoriteDistrict> findByUserIdWithDistrict(@Param("userId") Long userId);
    
    /**
     * 사용자별 관심 지역 존재 여부 확인
     */
    boolean existsByUserIdAndDistrictId(Long userId, Long districtId);
}
