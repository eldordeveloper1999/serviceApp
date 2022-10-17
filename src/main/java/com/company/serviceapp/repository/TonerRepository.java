package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Toner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TonerRepository extends JpaRepository<Toner, UUID> {
}
