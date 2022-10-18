package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Kartrij;
import com.company.serviceapp.projection.ReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface KartrijRepository extends JpaRepository<Kartrij, UUID> {

    @Query(nativeQuery = true, value = "select kartrijs.name as name, sum(count) as count, inventar_number as inventorNumber, array_agg(d.name) as department " +
            "from kartrijs " +
            "join departments d on kartrijs.department_id = d.id " +
            "group by inventar_number, kartrijs.name;")
    List<ReportProjection> getKartrij();
}
