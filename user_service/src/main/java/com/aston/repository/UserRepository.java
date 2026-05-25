package com.aston.repository;

import com.aston.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// интерфейс Spring Data JPA
public interface UserRepository extends JpaRepository<User, Long> {
}