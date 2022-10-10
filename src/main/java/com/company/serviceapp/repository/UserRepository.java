package com.company.serviceapp.repository;

import com.company.serviceapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

   User findByUsername(String username);

    boolean existsByUsername(String username);
}
