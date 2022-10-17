package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Rakel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RakelRepository extends JpaRepository<Rakel, UUID> {
}
