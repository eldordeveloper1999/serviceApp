package com.company.serviceapp.dto;


import com.company.serviceapp.model.Printer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import javax.persistence.ManyToOne;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class ProductDto {

    String name;

    String inventarNumber;

    Integer count;

    String  printerId;
}
