package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Lezva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LezvaRepository extends JpaRepository<Lezva, UUID> {
}
