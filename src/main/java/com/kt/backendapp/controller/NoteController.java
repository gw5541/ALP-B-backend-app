package com.kt.backendapp.controller;

import com.kt.backendapp.dto.NoteCreateRequest;
import com.kt.backendapp.dto.NoteDto;
import com.kt.backendapp.dto.NoteUpdateRequest;
import com.kt.backendapp.service.NoteService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 메모 컨트롤러
 */
// @Tag(name = "Notes", description = "메모 API")
@RestController
@RequestMapping("/api/v1/users/{userId}/notes")
@RequiredArgsConstructor
public class NoteController {
    
    private final NoteService noteService;
    
    // @Operation(summary = "메모 목록 조회", description = "사용자의 메모 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<List<NoteDto>> getNotes(
            // @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            
            // @Parameter(description = "자치구 ID 필터")
            @RequestParam(required = false) Long districtId
    ) {
        List<NoteDto> notes = noteService.getNotes(userId, districtId);
        return ResponseEntity.ok(notes);
    }
    
    // @Operation(summary = "메모 생성", description = "새로운 메모를 생성합니다")
    @PostMapping
    public ResponseEntity<NoteDto> createNote(
            // @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            
            @Valid @RequestBody NoteCreateRequest request
    ) {
        NoteDto note = noteService.createNote(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }
    
    // @Operation(summary = "메모 수정", description = "기존 메모를 수정합니다")
    @PutMapping("/{noteId}")
    public ResponseEntity<NoteDto> updateNote(
            // @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            
            // @Parameter(description = "메모 ID", required = true)
            @PathVariable Long noteId,
            
            @RequestBody NoteUpdateRequest request
    ) {
        NoteDto note = noteService.updateNote(userId, noteId, request);
        return ResponseEntity.ok(note);
    }
    
    // @Operation(summary = "메모 삭제", description = "메모를 삭제합니다")
    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(
            // @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            
            // @Parameter(description = "메모 ID", required = true)
            @PathVariable Long noteId
    ) {
        noteService.deleteNote(userId, noteId);
        return ResponseEntity.noContent().build();
    }
}
