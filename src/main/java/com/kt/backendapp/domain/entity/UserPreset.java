package com.kt.backendapp.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 사용자 프리셋 엔티티
 */
@Entity
@Table(name = "user_preset")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPreset {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preset_id")
    private Long presetId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "filters", columnDefinition = "jsonb")
    private Map<String, Object> filters;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserAccount user;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
