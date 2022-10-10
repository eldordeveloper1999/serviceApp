package com.company.serviceapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class Notification {

    String Id;

    String title;

    String department;

    String status;

    LocalDate date;

    LocalTime start_time;
}
