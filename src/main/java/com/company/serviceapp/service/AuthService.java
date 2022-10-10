package com.company.serviceapp.service;

import com.company.serviceapp.dto.UserDto;
import com.company.serviceapp.model.Department;
import com.company.serviceapp.model.User;
import com.company.serviceapp.repository.DepartmentRepository;
import com.company.serviceapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService{

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UserRepository userRepository;

    public List<Department> getAllDepartments() {

        return departmentRepository.findAll();

    }

    public void save(UserDto userDto) {

        Department department = departmentRepository.getByName(userDto.getDepartmentName());

        User user = new User(userDto.getFullName(), userDto.getUsername(), userDto.getPassword(), department, "ROLE_USER");
        userRepository.save(user);
    }

}
