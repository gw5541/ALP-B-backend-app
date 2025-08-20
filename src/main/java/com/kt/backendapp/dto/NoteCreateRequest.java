package com.kt.backendapp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 메모 생성 요청 DTO
 */
@Getter
@Setter
public class NoteCreateRequest {
    
    private Long districtId;
    private String content;
}
