package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Toner;
import com.company.serviceapp.projection.ReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TonerRepository extends JpaRepository<Toner, UUID> {
    @Query(nativeQuery = true, value = "select toners.name as name, sum(count) as countE, inventar_number as inventorNumber " +
            "from toners\n" +
            "         join departments d on toners.department_id = d.id\n" +
            "where DATE_PART('month', toners.date) = :date "+
            "group by inventar_number, toners.name;")
    List<ReportProjection> getToneRZapravka(Integer date);
}
