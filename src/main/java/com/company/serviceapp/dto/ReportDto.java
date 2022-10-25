package com.company.serviceapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class ReportDto {

    String name;

    String inventorNumber;

    Integer count;

    List<String> departments;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String department : departments) {
            result.append(",").append(department);
        }
        return result.toString();
    }
}
