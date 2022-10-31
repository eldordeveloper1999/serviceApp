package com.company.serviceapp.projection;

import com.company.serviceapp.dto.ReportDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.util.List;

@Projection(types = ReportDto.class)
public interface PlyonkaProjection {

    String getName();

    String getInventorNumber();

    Integer getCountE();

    @Value("#{@departmentRepository.getFindByPlyonkaInventorNumber(target.inventorNumber)}")
    List<String> getDepartmentName();

    LocalDate getDate();
}
