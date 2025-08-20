package com.kt.backendapp.repository;

import com.kt.backendapp.domain.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자 계정 Repository
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    
    Optional<UserAccount> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
