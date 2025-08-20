package com.kt.backendapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 메모 생성 요청 DTO
 */
@Getter
@Setter
public class NoteCreateRequest {
    
    private Long districtId;
    
    @NotBlank(message = "메모 제목은 필수입니다")
    private String title;
    
    private String content;
}
