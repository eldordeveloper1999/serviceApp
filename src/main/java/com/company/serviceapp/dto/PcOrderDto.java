package com.company.serviceapp.dto;

import com.company.serviceapp.model.Department;
import com.company.serviceapp.model.Status;
import com.company.serviceapp.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class PcOrderDto {

    UUID id;

    String title;

    String taskDescription;

    String department;

    LocalTime start_time;

    LocalDate start_date;

    LocalTime time;

    LocalDate date;

    String answerName;

    Boolean is_full;

    Boolean is_accept;

    String inventarNumber;

    String description;

    public PcOrderDto(UUID id, String title, String taskDescription, String department, LocalTime start_time,
                      LocalDate start_date, LocalTime time, LocalDate date, Boolean is_full,
                      String inventarNumber, String description, boolean is_accept) {
        this.id = id;
        this.title = title;
        this.taskDescription = taskDescription;
        this.department = department;
        this.start_time = start_time;
        this.start_date = start_date;
        this.time = time;
        this.date = date;
        this.is_full = is_full;
        this.inventarNumber = inventarNumber;
        this.description = description;
        this.is_accept = is_accept;
    }
}
