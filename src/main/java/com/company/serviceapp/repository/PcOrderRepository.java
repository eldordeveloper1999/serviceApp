package com.company.serviceapp.repository;

import com.company.serviceapp.model.Order;
import com.company.serviceapp.model.PCOrder;
import com.company.serviceapp.projection.OrderProjection;
import com.company.serviceapp.projection.OrderProjectionForClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PcOrderRepository extends JpaRepository<PCOrder, UUID> {

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status " +
            "from pc_orders O " +
            "JOIN tasks t on t.id = O.task_id " +
            "join departments d on d.id = O.department_id\n" +
            "join status s on s.id = O.status_id " +
            "where is_finished = false")
    List<OrderProjection> getPcOrdersForProjection();

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status, o.end_time\n" +
            "from pc_orders o\n" +
            "         join users u on o.department_id = u.department_id\n" +
            "         join departments d on d.id = o.department_id\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "where o.is_full = true\n" +
            "  and o.is_finished = true")
    List<OrderProjectionForClient> getFinishedPcOrdersForProjection();


    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status, o.end_time\n" +
            "from pc_orders o\n" +
            "         join users u on o.department_id = u.department_id\n" +
            "         join departments d on d.id = o.department_id\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "where o.is_full = true\n" +
            "  and o.is_finished = true")
    List<OrderProjectionForClient> getMonthlyOrdersByClient();

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status, o.end_time\n" +
            "from pc_orders o\n" +
            "         join users u on o.department_id = u.department_id\n" +
            "         join departments d on d.id = o.department_id\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "where o.is_full = true\n" +
            "  and o.is_finished = true")
    List<OrderProjectionForClient> getQuarterlyOrdersByClient();

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status, o.end_time\n" +
            "from pc_orders o\n" +
            "         join users u on o.department_id = u.department_id\n" +
            "         join departments d on d.id = o.department_id\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "where o.is_full = true\n" +
            "  and o.is_finished = true")
    List<OrderProjectionForClient> getYearlyOrdersByClient();

    @Query(nativeQuery = true, value = "select * from pc_orders where date = :localDate")
    List<PCOrder> getAllDailyPcOrders(LocalDate localDate);

    @Query(nativeQuery = true, value = "select count(*) from pc_orders where date = :localDate and is_finished = true and is_full = true")
    Integer getDailyFinishedPcOrdersCount(LocalDate localDate);

    @Query(nativeQuery = true, value = "select count(*) from pc_orders where date = :localDate and is_finished = false and is_full = false")
    Integer getDailyUnFinishedPcOrdersCount(LocalDate localDate);

    @Query(nativeQuery = true, value = "select * from pc_orders where is_full = false and is_accept = false;")
    List<PCOrder> getUnfinishedTasks(UUID id);

    @Query(nativeQuery = true, value = "select * from pc_orders p join users u on p.department_id = u.department_id\n" +
            "where p.is_accept = false and u.id = :id")
    PCOrder getByClientId(UUID id);
}
