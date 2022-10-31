package com.company.serviceapp.projection;

import com.company.serviceapp.dto.ReportDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.util.List;

@Projection(types = ReportDto.class)
public interface ValProjection {

    String getName();

    String getInventorNumber();

    Integer getCountE();

    @Value("#{@departmentRepository.getFindByValInventorNumber(target.inventorNumber)}")
    List<String> getDepartmentName();

    LocalDate getDate();
}
