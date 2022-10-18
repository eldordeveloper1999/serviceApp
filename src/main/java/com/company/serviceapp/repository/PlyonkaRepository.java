package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Plyonka;
import com.company.serviceapp.projection.ReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PlyonkaRepository extends JpaRepository<Plyonka, UUID> {
    @Query(nativeQuery = true, value = "select plyonkas.name as name, sum(count) as count, inventar_number as inventorNumber, array_agg(d.name) as department " +
            " from plyonkas " +
            " join departments d on plyonkas.department_id = d.id " +
            " group by inventar_number, plyonkas.name;")
    List<ReportProjection> getPlyonka();
}
