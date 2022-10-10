package com.company.serviceapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "expenses")
@PackagePrivate
public class Expense {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    UUID id;

    String texnikaNomi;

    String inventarNumber;

    @ManyToOne
    Department department;

    LocalDate buzilganSana;

    LocalDate tuzatilganSana;

    @ManyToOne
    User user;

//    @ManyToOne
//    Order order;

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

    public Expense(String texnikaNomi, String inventarNumber, Department department,
                   LocalDate buzilganSana, LocalDate tuzatilganSana, User user,
                   Integer toldirilganKartrijSoni, Integer almashtirilganBarabanSoni,
                   Integer almashtirilganValSoni, Integer almashtirilganPlyonkaSoni,
                   Integer almashtirilganRakelSoni, Integer almashtirilganLezvaSoni,
                   Integer almashtirilganKartrijSoni) {
        this.texnikaNomi = texnikaNomi;
        this.inventarNumber = inventarNumber;
        this.department = department;
        this.buzilganSana = buzilganSana;
        this.tuzatilganSana = tuzatilganSana;
        this.user = user;
        this.toldirilganKartrijSoni = toldirilganKartrijSoni;
        this.almashtirilganBarabanSoni = almashtirilganBarabanSoni;
        this.almashtirilganValSoni = almashtirilganValSoni;
        this.almashtirilganPlyonkaSoni = almashtirilganPlyonkaSoni;
        this.almashtirilganRakelSoni = almashtirilganRakelSoni;
        this.almashtirilganLezvaSoni = almashtirilganLezvaSoni;
        this.almashtirilganKartrijSoni = almashtirilganKartrijSoni;
    }


    public Expense(String texnikaNomi, String inventarNumber, Department department, LocalDate buzilganSana, LocalDate tuzatilganSana,
                   User user, Integer toldirilganKartrijSoni, String inventarToneForZapravka, Integer almashtirilganBarabanSoni, String inventarBaraban,
                   Integer almashtirilganValSoni, String inventarVal, Integer almashtirilganPlyonkaSoni, String inventarPlyonka, Integer almashtirilganRakelSoni,
                   String inventarRakel, Integer almashtirilganLezvaSoni, String inventarLezva, Integer almashtirilganKartrijSoni, String inventarKartrij) {
        this.texnikaNomi = texnikaNomi;
        this.inventarNumber = inventarNumber;
        this.department = department;
        this.buzilganSana = buzilganSana;
        this.tuzatilganSana = tuzatilganSana;
        this.user = user;
        this.toldirilganKartrijSoni = toldirilganKartrijSoni;
        this.inventarToneForZapravka = inventarToneForZapravka;
        this.almashtirilganBarabanSoni = almashtirilganBarabanSoni;
        this.inventarBaraban = inventarBaraban;
        this.almashtirilganValSoni = almashtirilganValSoni;
        this.inventarVal = inventarVal;
        this.almashtirilganPlyonkaSoni = almashtirilganPlyonkaSoni;
        this.inventarPlyonka = inventarPlyonka;
        this.almashtirilganRakelSoni = almashtirilganRakelSoni;
        this.inventarRakel = inventarRakel;
        this.almashtirilganLezvaSoni = almashtirilganLezvaSoni;
        this.inventarLezva = inventarLezva;
        this.almashtirilganKartrijSoni = almashtirilganKartrijSoni;
        this.inventarKartrij = inventarKartrij;
    }
}
