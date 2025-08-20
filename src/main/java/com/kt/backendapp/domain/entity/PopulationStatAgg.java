package com.kt.backendapp.domain.entity;

import com.kt.backendapp.domain.type.PeriodType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

/**
 * 인구 통계 집계 데이터 (기간 단위)
 */
@Entity
@Table(name = "population_stat_agg")
@IdClass(PopulationStatAgg.PopulationStatAggId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PopulationStatAgg {
    
    @Id
    @Column(name = "district_id")
    private Long districtId;
    
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "period_type")
    private PeriodType periodType;
    
    @Id
    @Column(name = "period_start_date")
    private LocalDate periodStartDate;
    

    
    @Column(name = "tot_avg")
    private Long totAvg;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "male_buckets_avg", columnDefinition = "jsonb")
    private Map<String, Number> maleBucketsAvg;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "female_buckets_avg", columnDefinition = "jsonb")
    private Map<String, Number> femaleBucketsAvg;
    
    @Column(name = "daytime_avg")
    private Long daytimeAvg;
    
    @Column(name = "nighttime_avg")
    private Long nighttimeAvg;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;
    
    /**
     * 복합키 클래스
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopulationStatAggId implements Serializable {
        private Long districtId;
        private PeriodType periodType;
        private LocalDate periodStartDate;
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            
            PopulationStatAggId that = (PopulationStatAggId) o;
            
            if (!districtId.equals(that.districtId)) return false;
            if (periodType != that.periodType) return false;
            return periodStartDate.equals(that.periodStartDate);
        }
        
        @Override
        public int hashCode() {
            int result = districtId.hashCode();
            result = 31 * result + periodType.hashCode();
            result = 31 * result + periodStartDate.hashCode();
            return result;
        }
    }
}
