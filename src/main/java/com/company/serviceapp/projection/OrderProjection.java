package com.company.serviceapp.projection;

import java.time.LocalDate;
import java.time.LocalTime;

public interface OrderProjection {

    String getId();

    String getTitle();

    String getDepartment();

    String getStatus();

    LocalDate getDate();

    LocalTime getStart_time();

}
