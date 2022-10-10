package com.company.serviceapp.repository;

import com.company.serviceapp.model.Printer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PrinterRepository extends JpaRepository<Printer, UUID> {

    @Query(nativeQuery = true, value = "select * from printers where model = :printerModel")
    Printer getByModel(String printerModel);
}
