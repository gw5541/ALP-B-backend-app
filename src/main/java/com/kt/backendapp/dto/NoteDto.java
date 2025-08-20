package com.kt.backendapp.dto;

import com.kt.backendapp.domain.entity.UserNote;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 메모 DTO
 */
@Getter
@Setter
public class NoteDto {
    
    private Long noteId;
    private Long userId;
    private Long districtId;
    private String districtName;
    private String content;
    private LocalDateTime createdAt;
    
    public NoteDto(UserNote entity) {
        this.noteId = entity.getNoteId();
        this.userId = entity.getUserId();
        this.districtId = entity.getDistrictId();
        this.districtName = entity.getDistrict() != null ? entity.getDistrict().getName() : null;
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
    }
}
