package com.company.serviceapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class PcExpenseDto {

        String title;

        String inventorNumber;

        String nameOfPcEquipment;

        String dateOfViolation;

        String fixedDate;

        Integer consumableProductCount;

        String consumableProductInventor;

        String department;
}
