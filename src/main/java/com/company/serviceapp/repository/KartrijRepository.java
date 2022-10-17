package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Kartrij;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KartrijRepository extends JpaRepository<Kartrij, UUID> {

}
