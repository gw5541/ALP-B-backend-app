package com.kt.backendapp.service;

import com.kt.backendapp.domain.entity.UserNote;
import com.kt.backendapp.dto.NoteCreateRequest;
import com.kt.backendapp.dto.NoteDto;
import com.kt.backendapp.dto.NoteUpdateRequest;
import com.kt.backendapp.exception.NotFoundException;
import com.kt.backendapp.repository.UserAccountRepository;
import com.kt.backendapp.repository.UserNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 메모 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteService {
    
    private final UserNoteRepository noteRepository;
    private final UserAccountRepository userRepository;
    private final DistrictService districtService;
    
    /**
     * 사용자별 메모 목록 조회
     */
    public List<NoteDto> getNotes(Long userId, Long districtId) {
        // 사용자 존재 확인
        validateUser(userId);
        
        // 지역 필터가 있으면 지역 존재 확인
        if (districtId != null) {
            districtService.getDistrictById(districtId);
        }
        
        List<UserNote> notes = noteRepository.findByUserIdAndDistrictId(userId, districtId);
        return notes.stream()
                .map(NoteDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * 메모 생성
     */
    @Transactional
    public NoteDto createNote(Long userId, NoteCreateRequest request) {
        // 사용자 존재 확인
        validateUser(userId);
        
        // 지역 ID가 있으면 지역 존재 확인
        if (request.getDistrictId() != null) {
            districtService.getDistrictById(request.getDistrictId());
        }
        
        UserNote note = new UserNote();
        note.setUserId(userId);
        note.setDistrictId(request.getDistrictId());
        note.setContent(request.getContent());
        
        UserNote saved = noteRepository.save(note);
        
        // 저장 후 조회하여 District 정보 포함
        UserNote result = noteRepository.findByUserIdAndDistrictId(userId, null).stream()
                .filter(n -> n.getNoteId().equals(saved.getNoteId()))
                .findFirst()
                .orElse(saved);
        
        return new NoteDto(result);
    }
    
    /**
     * 메모 수정
     */
    @Transactional
    public NoteDto updateNote(Long userId, Long noteId, NoteUpdateRequest request) {
        // 사용자 존재 확인
        validateUser(userId);
        
        UserNote note = noteRepository.findByNoteIdAndUserId(noteId, userId)
                .orElseThrow(() -> new NotFoundException("메모를 찾을 수 없습니다"));
        
        // 지역 ID가 변경되면 지역 존재 확인
        if (request.getDistrictId() != null) {
            districtService.getDistrictById(request.getDistrictId());
            note.setDistrictId(request.getDistrictId());
        }
        
        if (request.getContent() != null) {
            note.setContent(request.getContent());
        }
        
        UserNote saved = noteRepository.save(note);
        
        // 저장 후 조회하여 District 정보 포함
        UserNote result = noteRepository.findByUserIdAndDistrictId(userId, null).stream()
                .filter(n -> n.getNoteId().equals(saved.getNoteId()))
                .findFirst()
                .orElse(saved);
        
        return new NoteDto(result);
    }
    
    /**
     * 메모 삭제
     */
    @Transactional
    public void deleteNote(Long userId, Long noteId) {
        // 사용자 존재 확인
        validateUser(userId);
        
        UserNote note = noteRepository.findByNoteIdAndUserId(noteId, userId)
                .orElseThrow(() -> new NotFoundException("메모를 찾을 수 없습니다"));
        
        noteRepository.delete(note);
    }
    
    /**
     * 사용자 존재 확인
     */
    private void validateUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("사용자를 찾을 수 없습니다: " + userId);
        }
    }
}
