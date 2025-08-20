package com.kt.backendapp.repository;

import com.kt.backendapp.domain.entity.UserNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 메모 Repository
 */
@Repository
public interface UserNoteRepository extends JpaRepository<UserNote, Long> {
    
    /**
     * 사용자별 메모 목록 조회 (지역 필터 가능)
     */
    @Query("SELECT n FROM UserNote n LEFT JOIN FETCH n.district WHERE n.userId = :userId " +
           "AND (:districtId IS NULL OR n.districtId = :districtId) " +
           "ORDER BY n.createdAt DESC")
    List<UserNote> findByUserIdAndDistrictId(@Param("userId") Long userId, @Param("districtId") Long districtId);
    
    /**
     * 사용자별 특정 메모 조회
     */
    Optional<UserNote> findByNoteIdAndUserId(Long noteId, Long userId);
}
