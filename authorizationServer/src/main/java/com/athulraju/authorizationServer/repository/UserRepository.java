package com.athulraju.authorizationServer.repository;


import com.athulraju.authorizationServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmailId(String emailId);
}
