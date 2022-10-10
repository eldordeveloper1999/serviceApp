package com.company.serviceapp.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface OrderProjectionForClient {

    String getId();

    String getTitle();

    String getDepartment();

    String getStatus();

    LocalDate getDate();

    LocalTime getStart_time();

    LocalDateTime getEnd_time();
}
