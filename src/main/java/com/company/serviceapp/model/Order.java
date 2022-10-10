package com.company.serviceapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "orders")
@PackagePrivate
public class Order {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    UUID id;

    @ManyToOne
    Task task;

    @ManyToOne
    Department department;

    @ManyToOne
    Status status;

    @OrderBy
    @Column(nullable = false, updatable = false)
    LocalTime start_time;

    @OrderBy
    @Column(nullable = false, updatable = false)
    LocalDate date;

    LocalDateTime end_time;

    Boolean is_finished;   //for admin

    Boolean is_full;   //for client

    @ManyToOne
    Answer answer;

    String inventarNumber;

    @ManyToOne
    Printer printer;

}
