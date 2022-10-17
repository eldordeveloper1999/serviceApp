package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Baraban;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BarabanRepository extends JpaRepository<Baraban, UUID> {
}
