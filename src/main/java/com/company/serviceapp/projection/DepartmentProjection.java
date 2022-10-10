package com.company.serviceapp.projection;

import com.company.serviceapp.model.Department;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Department.class)
public interface DepartmentProjection {

    String getId();

    String getName();

    @Value("#{@orderRepository.getCountOrdersByDepartmentId(target.id) }")
    String getCountTasks();
}
