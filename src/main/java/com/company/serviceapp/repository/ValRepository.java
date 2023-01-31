package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Val;
import com.company.serviceapp.projection.ReportProjection;
import com.company.serviceapp.projection.ValProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ValRepository extends JpaRepository<Val, UUID> {
    @Query(nativeQuery = true, value = "select vals.name as name, sum(count) as countE, inventar_number as inventorNumber " +
            " from vals " +
            " join departments d on vals.department_id = d.id " +
            "where DATE_PART('month', vals.date) = :date "+
            " group by inventar_number, vals.name;")
    List<ValProjection> getVal(Integer date);
}
