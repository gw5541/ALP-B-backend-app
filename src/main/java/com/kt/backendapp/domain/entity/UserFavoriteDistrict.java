package com.kt.backendapp.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 사용자 관심 지역 엔티티
 */
@Entity
@Table(name = "user_favorite_district")
@IdClass(UserFavoriteDistrict.UserFavoriteDistrictId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFavoriteDistrict {
    
    @Id
    @Column(name = "user_id")
    private Long userId;
    
    @Id
    @Column(name = "district_id")
    private Long districtId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserAccount user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    /**
     * 복합키 클래스
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserFavoriteDistrictId implements Serializable {
        private Long userId;
        private Long districtId;
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            
            UserFavoriteDistrictId that = (UserFavoriteDistrictId) o;
            
            if (!userId.equals(that.userId)) return false;
            return districtId.equals(that.districtId);
        }
        
        @Override
        public int hashCode() {
            int result = userId.hashCode();
            result = 31 * result + districtId.hashCode();
            return result;
        }
    }
}
