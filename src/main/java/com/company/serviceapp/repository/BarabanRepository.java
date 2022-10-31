package com.company.serviceapp.repository;

import com.company.serviceapp.entity.Baraban;
import com.company.serviceapp.projection.BarabanProjection;
import com.company.serviceapp.projection.ReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BarabanRepository extends JpaRepository<Baraban, UUID> {
    @Query(nativeQuery = true, value = "select barabans.name as name, sum(count) as countE, " +
            "inventar_number as inventorNumber, barabans.date " +
            "from barabans " +
            "join departments d on barabans.department_id = d.id " +
            "group by inventar_number, barabans.name, barabans.date;")
    List<BarabanProjection> getBaraban();
}
