package com.example.fluxcard.repository;

import com.example.fluxcard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByMarqetaTokenIsNull();
}
