package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Lezva;
import com.company.serviceapp.projection.LezvaProjection;
import com.company.serviceapp.projection.ReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LezvaRepository extends JpaRepository<Lezva, UUID> {
    @Query(nativeQuery = true, value = "select lezvas.name as name, sum(count) as countE, inventar_number as inventorNumber " +
            " from lezvas " +
            " join departments d on lezvas.department_id = d.id " +
            "where DATE_PART('month', lezvas.date) = :date "+
            " group by inventar_number, lezvas.name;")
    List<LezvaProjection> getLezva(Integer date);
}
