package com.company.serviceapp.dto;

import com.company.serviceapp.model.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    String fullName;

    String username;

    String password;

    String departmentName;
}
