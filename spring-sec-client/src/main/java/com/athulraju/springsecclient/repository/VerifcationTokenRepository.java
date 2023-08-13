package com.athulraju.springsecclient.repository;

import com.athulraju.springsecclient.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifcationTokenRepository extends JpaRepository<VerificationToken,Long> {


     VerificationToken findBytoken(String token);
}
