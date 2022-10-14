package com.company.serviceapp.repository;

import com.company.serviceapp.model.Task;
import com.company.serviceapp.projection.OrderProjectionForClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query(nativeQuery = true, value = "select * from tasks t " +
            "JOIN orders o on t.id = O.task_id " +
            "where o.id = :id"
    )
    Task getTaskByOrderId(UUID id);

    @Query(nativeQuery = true, value = "select * from tasks where title = :zapravka_kartrij")
    Task findByTitle(String zapravka_kartrij);

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status, o.end_time\n" +
            "from orders o\n" +
            "         join users u on o.department_id = u.department_id\n" +
            "         join departments d on d.id = o.department_id\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "where o.is_full = true\n" +
            "  and o.is_finished = true")
    List<OrderProjectionForClient> getMonthlyOrders();

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status, o.end_time\n" +
            "from orders o\n" +
            "         join users u on o.department_id = u.department_id\n" +
            "         join departments d on d.id = o.department_id\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "where o.is_full = true\n" +
            "  and o.is_finished = true")
    List<OrderProjectionForClient> getQuarterlyOrders();

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status, o.end_time\n" +
            "from orders o\n" +
            "         join users u on o.department_id = u.department_id\n" +
            "         join departments d on d.id = o.department_id\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "where o.is_full = true\n" +
            "  and o.is_finished = true")
    List<OrderProjectionForClient> getYearlyOrders();
}
