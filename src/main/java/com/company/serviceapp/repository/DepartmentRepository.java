package com.company.serviceapp.repository;

import com.company.serviceapp.model.Department;
import com.company.serviceapp.projection.DepartmentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    @Query(nativeQuery = true, value = "select distinct cast(d.id as varchar), d.name from departments d")
    List<DepartmentProjection> getDepartmentOrdersCount();


    @Query(nativeQuery = true, value = "select * from departments where name = :department")
    Department getByName(String department);

    @Query(nativeQuery = true, value = "select * from departments d join users u on d.id = u.department_id" +
            " where u.id= :userId")
    Department getByUserId(UUID userId);

    @Query(nativeQuery = true, value = "select d.short_name from departments d join toners t on d.id = t.department_id where t.inventar_number = :inventar")
    List<String> getFindByInventorNumber(String inventar);

    @Query(nativeQuery = true, value = "select d.short_name from departments d join barabans b on d.id = b.department_id where b.inventar_number = :inventar")
    List<String> getFindByBarabanInventorNumber(String inventar);

    @Query(nativeQuery = true, value = "select d.short_name from departments d join rakels r on d.id = r.department_id where r.inventar_number = :inventar")
    List<String> getFindByRakelInventorNumber(String inventar);

    @Query(nativeQuery = true, value = "select d.short_name from departments d join kartrijs r on d.id = r.department_id where r.inventar_number = :inventar")
    List<String> getFindByKartrijInventorNumber(String inventar);

    @Query(nativeQuery = true, value = "select d.short_name from departments d join lezvas r on d.id = r.department_id where r.inventar_number = :inventar")
    List<String> getFindByLezvaInventorNumber(String inventar);

    @Query(nativeQuery = true, value = "select d.short_name from departments d join plyonkas r on d.id = r.department_id where r.inventar_number = :inventar")
    List<String> getFindByPlyonkaInventorNumber(String inventar);

    @Query(nativeQuery = true, value = "select d.short_name from departments d join vals r on d.id = r.department_id where r.inventar_number = :inventar")
    List<String> getFindByValInventorNumber(String inventar);


}
