package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Plyonka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlyonkaRepository extends JpaRepository<Plyonka, UUID> {
}
