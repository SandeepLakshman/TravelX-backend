package com.travel.travelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.travelmanagement.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}