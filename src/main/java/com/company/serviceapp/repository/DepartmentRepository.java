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
}
