package com.kt.backendapp.controller;

import com.kt.backendapp.dto.FavoriteCreateRequest;
import com.kt.backendapp.dto.FavoriteDto;
import com.kt.backendapp.service.FavoriteService;
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
 * 관심 지역 컨트롤러
 */
// @Tag(name = "Favorites", description = "관심 지역 API")
@RestController
@RequestMapping("/api/v1/users/{userId}/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    
    private final FavoriteService favoriteService;
    
    // @Operation(summary = "관심 지역 목록 조회", description = "사용자의 관심 지역 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<List<FavoriteDto>> getFavorites(
            // @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId
    ) {
        List<FavoriteDto> favorites = favoriteService.getFavorites(userId);
        return ResponseEntity.ok(favorites);
    }
    
    // @Operation(summary = "관심 지역 추가", description = "새로운 관심 지역을 추가합니다")
    @PostMapping
    public ResponseEntity<FavoriteDto> addFavorite(
            // @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            
            @Valid @RequestBody FavoriteCreateRequest request
    ) {
        FavoriteDto favorite = favoriteService.addFavorite(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
    }
    
    // @Operation(summary = "관심 지역 삭제", description = "관심 지역을 삭제합니다")
    @DeleteMapping("/{districtId}")
    public ResponseEntity<Void> removeFavorite(
            // @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            
            // @Parameter(description = "자치구 ID", required = true)
            @PathVariable Long districtId
    ) {
        favoriteService.removeFavorite(userId, districtId);
        return ResponseEntity.noContent().build();
    }
}
