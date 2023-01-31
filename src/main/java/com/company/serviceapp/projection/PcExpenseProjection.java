package com.company.serviceapp.projection;

import com.company.serviceapp.dto.ReportDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.util.List;

@Projection(types = ReportDto.class)
public interface PcExpenseProjection {

    String getName();

    String getInventorNumber();

    Integer getCountE();

//    LocalDate getDate();

    @Value("#{@departmentRepository.getFindByPcEquipmentInventorNumber(target.inventorNumber)}")
    List<String> getDepartmentName();
}
