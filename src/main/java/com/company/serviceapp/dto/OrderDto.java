package com.company.serviceapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class OrderDto {

    UUID id;

    String title;

    String description;

    String department;

    LocalTime start_time;

    LocalDate start_date;

    LocalTime time;

    LocalDate date;

    String answerName;

    Boolean is_full;

    Boolean is_finished;

    Boolean is_accept;

    String inventarNumber;

    String printerModel;

    public OrderDto(UUID id, String title, String description,
                    String department, LocalTime start_time,
                    LocalDate start_date, LocalTime time, LocalDate date,
                    Boolean is_full, Boolean is_finished, Boolean is_accept, String inventarNumber, String printerModel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.department = department;
        this.start_time = start_time;
        this.start_date = start_date;
        this.time = time;
        this.date = date;
        this.is_full = is_full;
        this.is_finished = is_finished;
        this.is_accept = is_accept;
        this.inventarNumber = inventarNumber;
        this.printerModel = printerModel;
    }

    public OrderDto(UUID id, String title, String description,
                    String department, LocalTime start_time,
                    LocalDate start_date, LocalTime time, LocalDate date,
                    String answerName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.department = department;
        this.start_time = start_time;
        this.start_date = start_date;
        this.time = time;
        this.date = date;
        this.answerName = answerName;
    }
}
