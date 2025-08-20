package com.kt.backendapp.controller;

import com.kt.backendapp.domain.type.PeriodType;
import com.kt.backendapp.dto.*;
import com.kt.backendapp.service.PopulationService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 인구 통계 컨트롤러
 */
// @Tag(name = "Population", description = "인구 통계 API")
@RestController
@RequestMapping("/api/v1/population")
@RequiredArgsConstructor
@Validated
public class PopulationController {
    
    private final PopulationService populationService;
    
    // @Operation(summary = "원본 데이터 조회", description = "인구 통계 원본 데이터를 페이지별로 조회합니다")
    @GetMapping("/raw")
    public ResponseEntity<PageResponse<PopulationRawDto>> getRawData(
            // @Parameter(description = "자치구 ID", required = true)
            @RequestParam Long districtId,
            
            // @Parameter(description = "시작 날짜", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            
            // @Parameter(description = "종료 날짜", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            
            // @Parameter(description = "시작 시간 (0-23)")
            @RequestParam(required = false) Long hourFrom,
            
            // @Parameter(description = "종료 시간 (0-23)")
            @RequestParam(required = false) Long hourTo,
            
            // @Parameter(description = "페이지 번호 (0부터 시작)")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            
            // @Parameter(description = "페이지 크기")
            @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        PageResponse<PopulationRawDto> response = populationService.getRawData(
                districtId, from, to, hourFrom, hourTo, page, size);
        return ResponseEntity.ok(response);
    }
    
    // @Operation(summary = "집계 통계 조회", description = "인구 통계 집계 데이터를 조회합니다")
    @GetMapping("/stats")
    public ResponseEntity<List<PopulationAggDto>> getStats(
            // @Parameter(description = "자치구 ID", required = true)
            @RequestParam Long districtId,
            
            // @Parameter(description = "집계 기간 타입", required = true)
            @RequestParam PeriodType period,
            
            // @Parameter(description = "시작 날짜", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            
            //  @Parameter(description = "종료 날짜", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            
            // @Parameter(description = "성별 필터")
            @RequestParam(required = false) String gender,
            
            // @Parameter(description = "연령대 필터")
            @RequestParam(required = false) String ageBucket
    ) {
        List<PopulationAggDto> response = populationService.getAggStats(
                districtId, period, from, to, gender, ageBucket);
        return ResponseEntity.ok(response);
    }
    
    // @Operation(summary = "시간별 트렌드 조회", description = "특정 날짜의 0~23시 인구 변화를 조회합니다")
    @GetMapping("/trends/hourly")
    public ResponseEntity<HourlyTrendDto> getHourlyTrend(
            // @Parameter(description = "자치구 ID", required = true)
            @RequestParam Long districtId,
            
            // @Parameter(description = "조회 날짜", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            
            // @Parameter(description = "성별 필터")
            @RequestParam(required = false) String gender,
            
            // @Parameter(description = "연령대 필터")
            @RequestParam(required = false) String ageBucket,
            
            // @Parameter(description = "지난주 같은 요일과 비교")
            @RequestParam(required = false, defaultValue = "false") String compare
    ) {
        boolean compareLastWeek = "lastWeek".equals(compare);
        HourlyTrendDto response = populationService.getHourlyTrend(
                districtId, date, gender, ageBucket, compareLastWeek);
        return ResponseEntity.ok(response);
    }
    
    // @Operation(summary = "월별 트렌드 조회", description = "최근 N개월의 월별 평균 인구 변화를 조회합니다")
    @GetMapping("/trends/monthly")
    public ResponseEntity<MonthlyTrendDto> getMonthlyTrend(
            // @Parameter(description = "자치구 ID", required = true)
            @RequestParam Long districtId,
            
            // @Parameter(description = "조회할 개월 수")
            @RequestParam(defaultValue = "6") @Min(1) int months,
            
            // @Parameter(description = "성별 필터")
            @RequestParam(required = false) String gender,
            
            // @Parameter(description = "연령대 필터")
            @RequestParam(required = false) String ageBucket
    ) {
        MonthlyTrendDto response = populationService.getMonthlyTrend(
                districtId, months, gender, ageBucket);
        return ResponseEntity.ok(response);
    }
    
    // @Operation(summary = "연령 분포 조회", description = "특정 기간의 연령별 인구 분포를 조회합니다")
    @GetMapping("/age-distribution")
    public ResponseEntity<AgeDistributionDto> getAgeDistribution(
            // @Parameter(description = "자치구 ID", required = true)
            @RequestParam Long districtId,
            
            // @Parameter(description = "시작 날짜", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            
            // @Parameter(description = "종료 날짜", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        AgeDistributionDto response = populationService.getAgeDistribution(districtId, from, to);
        return ResponseEntity.ok(response);
    }
}
