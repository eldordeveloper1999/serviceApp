package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Val;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ValRepository extends JpaRepository<Val, UUID> {
}
