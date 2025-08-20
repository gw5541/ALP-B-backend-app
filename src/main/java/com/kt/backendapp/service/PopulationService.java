package com.kt.backendapp.service;

import com.kt.backendapp.domain.entity.PopulationStatAgg;
import com.kt.backendapp.domain.entity.PopulationStatRaw;
import com.kt.backendapp.domain.type.PeriodType;
import com.kt.backendapp.dto.*;
import com.kt.backendapp.exception.BadRequestException;
import com.kt.backendapp.repository.PopulationStatAggRepository;
import com.kt.backendapp.repository.PopulationStatRawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 인구 통계 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PopulationService {
    
    private final PopulationStatRawRepository rawRepository;
    private final PopulationStatAggRepository aggRepository;
    private final DistrictService districtService;
    
    /**
     * 원본 데이터 조회 (페이지네이션)
     */
    public PageResponse<PopulationRawDto> getRawData(Long districtId, LocalDate from, LocalDate to, 
                                                     Long hourFrom, Long hourTo, int page, int size) {
        // 자치구 존재 확인
        districtService.getDistrictById(districtId);
        
        // 날짜 검증
        if (from.isAfter(to)) {
            throw new BadRequestException("시작 날짜가 종료 날짜보다 늦을 수 없습니다");
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<PopulationStatRaw> rawPage = rawRepository.findByConditions(
                districtId, from, to, hourFrom, hourTo, pageable);
        
        Page<PopulationRawDto> dtoPage = rawPage.map(PopulationRawDto::new);
        return new PageResponse<>(dtoPage);
    }
    
    /**
     * 집계 통계 조회
     */
    public List<PopulationAggDto> getAggStats(Long districtId, PeriodType period, LocalDate from, LocalDate to,
                                              String gender, String ageBucket) {
        // 자치구 존재 확인
        districtService.getDistrictById(districtId);
        
        // 집계 데이터 우선 조회
        List<PopulationStatAgg> aggList = aggRepository.findByConditions(districtId, period, from, to);
        
        if (!aggList.isEmpty()) {
            return aggList.stream()
                    .map(PopulationAggDto::new)
                    .collect(Collectors.toList());
        }
        
        // 집계 데이터가 없으면 원본 데이터로 on-the-fly 계산
        List<PopulationStatRaw> rawList = rawRepository.findByDistrictIdAndDateRange(districtId, from, to);
        
        if (rawList.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 기간별 그룹핑하여 평균 계산 (간단한 구현)
        Map<String, Number> avgMaleBuckets = calculateAverageBuckets(rawList, true);
        Map<String, Number> avgFemaleBuckets = calculateAverageBuckets(rawList, false);
        long avgTotal = (long) rawList.stream().mapToLong(PopulationStatRaw::getTot).average().orElse(0);
        
        PopulationAggDto dto = new PopulationAggDto();
        dto.setDistrictId(districtId);
        dto.setPeriodType(period);
        dto.setPeriodStartDate(from);
        dto.setTotalAvg(avgTotal);
        dto.setMaleBucketsAvg(avgMaleBuckets);
        dto.setFemaleBucketsAvg(avgFemaleBuckets);
        
        return Collections.singletonList(dto);
    }
    
    /**
     * 시간별 트렌드 조회
     */
    public HourlyTrendDto getHourlyTrend(Long districtId, LocalDate date, String gender, String ageBucket, 
                                         boolean compareLastWeek) {
        // 자치구 존재 확인
        var district = districtService.getDistrictById(districtId);
        
        // 현재 날짜 데이터 조회
        List<PopulationStatRaw> currentData = rawRepository.findByDistrictIdAndDate(districtId, date);
        List<HourlyTrendDto.HourlyData> currentHourlyData = currentData.stream()
                .map(raw -> new HourlyTrendDto.HourlyData(
                        raw.getTmzonPdSe(), raw.getTot(), raw.getMaleBuckets(), raw.getFemaleBuckets()))
                .collect(Collectors.toList());
        
        List<HourlyTrendDto.HourlyData> compareData = null;
        if (compareLastWeek) {
            LocalDate compareDate = date.minusWeeks(1);
            List<PopulationStatRaw> compareRawData = rawRepository.findByDistrictIdAndDate(districtId, compareDate);
            compareData = compareRawData.stream()
                    .map(raw -> new HourlyTrendDto.HourlyData(
                            raw.getTmzonPdSe(), raw.getTot(), raw.getMaleBuckets(), raw.getFemaleBuckets()))
                    .collect(Collectors.toList());
        }
        
        return new HourlyTrendDto(districtId, district.getName(), date, currentHourlyData, compareData);
    }
    
    /**
     * 월별 트렌드 조회
     */
    public MonthlyTrendDto getMonthlyTrend(Long districtId, int months, String gender, String ageBucket) {
        // 자치구 존재 확인
        var district = districtService.getDistrictById(districtId);
        
        // 최근 N개월 데이터 조회
        LocalDate fromDate = LocalDate.now().minusMonths(months).withDayOfMonth(1);
        List<PopulationStatAgg> monthlyAggList = aggRepository.findRecentMonthlyData(districtId, fromDate);
        
        List<MonthlyTrendDto.MonthlyData> monthlyDataList = monthlyAggList.stream()
                .map(agg -> new MonthlyTrendDto.MonthlyData(
                        YearMonth.from(agg.getPeriodStartDate()).toString(),
                        agg.getTotAvg(),
                        agg.getMaleBucketsAvg(),
                        agg.getFemaleBucketsAvg()))
                .collect(Collectors.toList());
        
        return new MonthlyTrendDto(districtId, district.getName(), monthlyDataList);
    }
    
    /**
     * 연령 분포 조회
     */
    public AgeDistributionDto getAgeDistribution(Long districtId, LocalDate from, LocalDate to) {
        // 자치구 존재 확인
        var district = districtService.getDistrictById(districtId);
        
        // 기간별 원본 데이터 조회하여 평균 계산
        List<PopulationStatRaw> rawList = rawRepository.findByDistrictIdAndDateRange(districtId, from, to);
        
        if (rawList.isEmpty()) {
            return new AgeDistributionDto(districtId, district.getName(), from, to, 
                    Collections.emptyMap(), Collections.emptyMap(), 0L);
        }
        
        Map<String, Number> avgMaleBuckets = calculateAverageBuckets(rawList, true);
        Map<String, Number> avgFemaleBuckets = calculateAverageBuckets(rawList, false);
        long avgTotal = (long) rawList.stream().mapToLong(PopulationStatRaw::getTot).average().orElse(0);
        
        return new AgeDistributionDto(districtId, district.getName(), from, to, 
                avgMaleBuckets, avgFemaleBuckets, avgTotal);
    }
    
    /**
     * 버킷별 평균 계산 헬퍼 메서드
     */
    private Map<String, Number> calculateAverageBuckets(List<PopulationStatRaw> rawList, boolean isMale) {
        if (rawList.isEmpty()) {
            return Collections.emptyMap();
        }
        
        Map<String, List<Number>> bucketGroups = new HashMap<>();
        
        for (PopulationStatRaw raw : rawList) {
            Map<String, Number> buckets = isMale ? raw.getMaleBuckets() : raw.getFemaleBuckets();
            if (buckets != null) {
                for (Map.Entry<String, Number> entry : buckets.entrySet()) {
                    bucketGroups.computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                               .add(entry.getValue());
                }
            }
        }
        
        Map<String, Number> avgBuckets = new HashMap<>();
        for (Map.Entry<String, List<Number>> entry : bucketGroups.entrySet()) {
            double avg = entry.getValue().stream()
                    .mapToDouble(Number::doubleValue)
                    .average()
                    .orElse(0.0);
            avgBuckets.put(entry.getKey(), (long) avg);
        }
        
        return avgBuckets;
    }
}
