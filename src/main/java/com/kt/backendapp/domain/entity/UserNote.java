package com.kt.backendapp.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용자 메모 엔티티
 */
@Entity
@Table(name = "user_note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long noteId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "district_id")
    private Long districtId;
    
    @Column(name = "content")
    private String content;
    
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
}
