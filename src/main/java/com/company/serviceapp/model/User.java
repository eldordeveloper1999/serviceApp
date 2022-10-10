package com.company.serviceapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
@PackagePrivate
public class User {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    UUID id;

    String fullName;

    boolean active = true;

    String username;

    String password;

    @ManyToOne
    Department department;

    String roles;

    public User(String fullName, String username, String password, Department department) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.department = department;
    }

    public User(String fullName, String username, String password, Department department, String roles) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.department = department;
        this.roles = roles;
    }

}
