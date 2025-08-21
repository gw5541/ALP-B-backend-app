package com.kt.backendapp.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 메모 수정 요청 DTO
 */
@Getter
@Setter
public class NoteUpdateRequest {
    
    private Long districtId;
    
    @Size(max = 500, message = "메모 내용은 500자를 초과할 수 없습니다")
    private String content;
}
