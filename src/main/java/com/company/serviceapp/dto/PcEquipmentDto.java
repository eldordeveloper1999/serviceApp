package com.company.serviceapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class PcEquipmentDto {

    String name;

    String inventorNumber;

    Integer count;
}
