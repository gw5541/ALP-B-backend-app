package com.kt.backendapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 메모 생성 요청 DTO
 */
@Getter
@Setter
public class NoteCreateRequest {
    
    private Long districtId;
    
    @NotBlank(message = "메모 내용은 필수입니다")
    @Size(max = 500, message = "메모 내용은 500 초과할 수 없습니다")
    private String content;
}
