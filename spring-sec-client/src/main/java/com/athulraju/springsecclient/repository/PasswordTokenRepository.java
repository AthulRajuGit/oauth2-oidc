package com.athulraju.springsecclient.repository;

import com.athulraju.springsecclient.entity.PasswordVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordVerificationToken,Long> {
    PasswordVerificationToken findByToken(String token);
}
