package com.company.serviceapp.repository;

import com.company.serviceapp.model.PcEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PcEquipmentsRepository extends JpaRepository<PcEquipment, UUID> {
    @Query(nativeQuery = true, value = "select * from pc_equipments where inventor_number = :inventorNumber")
    PcEquipment getByInventor(String inventorNumber);
}
