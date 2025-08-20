package com.kt.backendapp.domain.entity;

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
 * 인구 통계 원본 데이터 (시간 단위)
 */
@Entity
@Table(name = "population_stat_raw")
@IdClass(PopulationStatRaw.PopulationStatRawId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PopulationStatRaw {
    
    @Id
    @Column(name = "district_id")
    private Long districtId;
    
    @Id
    @Column(name = "stdr_de_id")
    private LocalDate stdrDeId;
    
    @Id
    @Column(name = "tmzon_pd_se")
    private Long tmzonPdSe; // 0~23 시간대
    
    @Column(name = "tot")
    private Long tot;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "male_buckets", columnDefinition = "jsonb")
    private Map<String, Number> maleBuckets;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "female_buckets", columnDefinition = "jsonb")
    private Map<String, Number> femaleBuckets;
    
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
    public static class PopulationStatRawId implements Serializable {
        private Long districtId;
        private LocalDate stdrDeId;
        private Long tmzonPdSe;
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            
            PopulationStatRawId that = (PopulationStatRawId) o;
            
            if (!districtId.equals(that.districtId)) return false;
            if (!stdrDeId.equals(that.stdrDeId)) return false;
            return tmzonPdSe.equals(that.tmzonPdSe);
        }
        
        @Override
        public int hashCode() {
            int result = districtId.hashCode();
            result = 31 * result + stdrDeId.hashCode();
            result = 31 * result + tmzonPdSe.hashCode();
            return result;
        }
    }
}
