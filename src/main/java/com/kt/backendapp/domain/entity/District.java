package com.kt.backendapp.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 자치구 엔티티
 */
@Entity
@Table(name = "district")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class District {
    
    @Id
    @Column(name = "district_id")
    private Long districtId;
    
    @Column(name = "name", nullable = false)
    private String name;
}
