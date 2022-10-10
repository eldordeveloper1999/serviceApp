package com.company.serviceapp.repository;

import com.company.serviceapp.model.PCExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PcExpenseRepository extends JpaRepository<PCExpense, UUID> {
}
