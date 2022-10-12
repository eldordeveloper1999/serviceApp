package com.company.serviceapp.repository;

import com.company.serviceapp.model.NewProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface NewProductRepository extends JpaRepository<NewProduct, UUID> {
    @Query(nativeQuery = true, value = "select distinct * from new_products where printer_id = :id")
    List<NewProduct> findByPrinterId(UUID id);
}
