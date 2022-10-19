package com.company.serviceapp.entity;

import com.company.serviceapp.model.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity(name = "plyonkas")
public class Plyonka {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    UUID id;

    String name;

    String inventarNumber;

    Integer count;

    LocalDate date;

    @ManyToOne
    Department department;
}
