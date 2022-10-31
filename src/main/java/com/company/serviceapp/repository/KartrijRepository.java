package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Kartrij;
import com.company.serviceapp.projection.KartrijProjection;
import com.company.serviceapp.projection.ReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface KartrijRepository extends JpaRepository<Kartrij, UUID> {

    @Query(nativeQuery = true, value = "select kartrijs.name as name, sum(count) as countE, inventar_number as inventorNumber, kartrijs.date " +
            "from kartrijs " +
            "join departments d on kartrijs.department_id = d.id " +
            "group by inventar_number, kartrijs.name, kartrijs.date;")
    List<KartrijProjection> getKartrij();
}
