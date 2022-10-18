package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Plyonka;
import com.company.serviceapp.projection.PlyonkaProjection;
import com.company.serviceapp.projection.ReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PlyonkaRepository extends JpaRepository<Plyonka, UUID> {
    @Query(nativeQuery = true, value = "select plyonkas.name as name, sum(count) as countE, inventar_number as inventorNumber " +
            " from plyonkas " +
            " join departments d on plyonkas.department_id = d.id " +
            " group by inventar_number, plyonkas.name;")
    List<PlyonkaProjection> getPlyonka();
}
