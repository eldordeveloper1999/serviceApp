package com.company.serviceapp.dto;

import com.company.serviceapp.model.Department;
import com.company.serviceapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class ExpenseDto {

    String texnikaNomi;

    String inventarNumber;

    String buzilganSana;

    String tuzatilganSana;

    Integer toldirilganKartrijSoni;

    String inventarToneForZapravka;

    Integer almashtirilganBarabanSoni;

    String inventarBaraban;

    Integer almashtirilganValSoni;

    String inventarVal;

    Integer almashtirilganPlyonkaSoni;

    String inventarPlyonka;

    Integer almashtirilganRakelSoni;

    String inventarRakel;

    Integer almashtirilganLezvaSoni;

    String inventarLezva;

    Integer almashtirilganKartrijSoni;

    String inventarKartrij;

    String department;


}
