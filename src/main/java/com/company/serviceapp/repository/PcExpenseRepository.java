package com.company.serviceapp.repository;

import com.company.serviceapp.model.PCExpense;
import com.company.serviceapp.projection.BarabanProjection;
import com.company.serviceapp.projection.PcExpenseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PcExpenseRepository extends JpaRepository<PCExpense, UUID> {

    @Query(nativeQuery = true, value = "select pe.name_of_equipment as name, pe.consumable_product_inventor as inventorNumber, sum(consumable_product_count) as countE " +
            "from pc_expenses pe " +
            "where DATE_PART('month', fixed_date) = :date "+
            "group by pe.consumable_product_inventor, pe.name_of_equipment;")
    List<PcExpenseProjection> getPcExpenses(Integer date);
}
