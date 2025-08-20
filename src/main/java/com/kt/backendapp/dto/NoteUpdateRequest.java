package com.kt.backendapp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 메모 수정 요청 DTO
 */
@Getter
@Setter
public class NoteUpdateRequest {
    
    private Long districtId;
    private String content;
}
