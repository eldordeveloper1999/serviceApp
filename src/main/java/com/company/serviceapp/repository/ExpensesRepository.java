package com.company.serviceapp.repository;

import com.company.serviceapp.dto.ReportDto;
import com.company.serviceapp.model.Expense;
import com.company.serviceapp.projection.ReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ExpensesRepository extends JpaRepository<Expense, UUID> {

    @Query(nativeQuery = true, value = "select e.inventar_baraban as inventorNumber, e.almashtirilgan_baraban_soni as count, np.name as name, d.name as departmentName from expenses e\n" +
            "join new_products np on e.inventar_baraban = np.inventar_number\n" +
            "join departments d on e.department_id = d.id;")
    List<ReportProjection> getBaraban();

    @Query(nativeQuery = true, value = "select e.inventar_kartrij as inventorNumber, e.almashtirilgan_kartrij_soni as count, np.name as name, d.name as departmentName from expenses e\n" +
            "join new_products np on e.inventar_kartrij = np.inventar_number\n" +
            "join departments d on e.department_id = d.id;")
    List<ReportProjection> getKartrij();

    @Query(nativeQuery = true, value = "select e.inventar_plyonka as inventorNumber, e.almashtirilgan_plyonka_soni as count, np.name as name, d.name as departmentName from expenses e\n" +
            "join new_products np on e.inventar_plyonka = np.inventar_number\n" +
            "join departments d on e.department_id = d.id;;")
    List<ReportProjection> getPlyonka();

    @Query(nativeQuery = true, value = "select e.inventar_lezva as inventorNumber, e.almashtirilgan_lezva_soni as count, np.name as name, d.name as departmentName from expenses e\n" +
            "join new_products np on e.inventar_lezva = np.inventar_number\n" +
            "join departments d on e.department_id = d.id;")
    List<ReportProjection> getLezva();

    @Query(nativeQuery = true, value = "select e.inventar_rakel as inventorNumber, e.almashtirilgan_rakel_soni as count, np.name as name, d.name as departmentName from expenses e\n" +
            "join new_products np on e.inventar_rakel = np.inventar_number\n" +
            "join departments d on e.department_id = d.id;;")
    List<ReportProjection> getRakel();

    @Query(nativeQuery = true, value = "select e.inventar_val as inventorNumber, e.almashtirilgan_val_soni as count, np.name as name, d.name as departmentName from expenses e\n" +
            "join new_products np on e.inventar_val = np.inventar_number\n" +
            "join departments d on e.department_id = d.id;")
    List<ReportProjection> getVal();

    @Query(nativeQuery = true, value = "select e.inventar_tone_for_zapravka as inventorNumber, e.toldirilgan_kartrij_soni as count, np.name as name, d.name as departmentName from expenses e\n" +
            "join new_products np on e.inventar_tone_for_zapravka = np.inventar_number\n" +
            "join departments d on e.department_id = d.id;")
    List<ReportProjection> getToneRZapravka();

}
