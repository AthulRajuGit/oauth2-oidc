package com.athulraju.springsecclient.repository;

import com.athulraju.springsecclient.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmailId(String emailId);
}
