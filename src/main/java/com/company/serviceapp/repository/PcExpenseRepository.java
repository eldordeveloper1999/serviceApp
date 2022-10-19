package com.company.serviceapp.repository;

import com.company.serviceapp.model.PCExpense;
import com.company.serviceapp.projection.BarabanProjection;
import com.company.serviceapp.projection.PcExpenseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PcExpenseRepository extends JpaRepository<PCExpense, UUID> {

    @Query(nativeQuery = true, value = "select p.name as name, pe.consumable_product_inventor as inventorNumber, sum(consumable_product_count) as countE\n" +
            "from pc_expenses pe\n" +
            "    join pc_equipments p on pe.consumable_product_inventor = p.inventor_number\n" +
            "group by pe.consumable_product_inventor, p.name, pe.fixed_date;")
    List<PcExpenseProjection> getPcExpenses();
}
