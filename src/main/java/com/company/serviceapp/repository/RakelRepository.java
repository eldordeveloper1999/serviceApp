package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Rakel;
import com.company.serviceapp.projection.RakelProjection;
import com.company.serviceapp.projection.ReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RakelRepository extends JpaRepository<Rakel, UUID> {

    @Query(nativeQuery = true, value = "select rakels.name as name, sum(count) as countE, inventar_number as inventorNumber " +
            " from rakels " +
            " join departments d on rakels.department_id = d.id " +
            "where DATE_PART('month', rakels.date) = :date "+
            " group by inventar_number, rakels.name;")
    List<RakelProjection> getRakel(Integer date);
}
