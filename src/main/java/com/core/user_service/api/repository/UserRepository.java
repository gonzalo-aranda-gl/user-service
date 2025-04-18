package com.core.user_service.api.repository;

import com.core.user_service.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findUserByEmail(String email);
}
