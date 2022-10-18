package com.company.serviceapp.projection;

import com.company.serviceapp.dto.ReportDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = ReportDto.class)
public interface KartrijProjection {

    String getName();

    String getInventorNumber();

    Integer getCountE();

    @Value("#{@departmentRepository.getFindByKartrijInventorNumber(target.inventorNumber)}")
    List<String> getDepartmentName();
}