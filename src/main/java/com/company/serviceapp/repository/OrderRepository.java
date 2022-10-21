package com.company.serviceapp.repository;

import com.company.serviceapp.model.Order;
import com.company.serviceapp.projection.OrderProjection;
import com.company.serviceapp.projection.OrderProjectionForClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query(nativeQuery = true, value = "select * from orders where department_id = :id and is_finished = false")
    List<Order> findByDepartmentId(UUID id);

    @Query(nativeQuery = true, value = "select * from orders where department_id = :id and is_finished = true")
    List<Order> findByDepartmentIdFinishedOrders(UUID id);

    @Query(nativeQuery = true, value = "select * from orders where date = :day")
    List<Order> getLastOrders(Date day);

    @Query(nativeQuery = true, value = "select * from orders " +
            "join answers a2 on a2.id = orders.answer_id\n" +
            "where a2.name = 'Bajarildi' and is_finished = true and is_full = true and is_accept = true and date = :localDate")
    List<Order> getDailyFinishedOrders(LocalDate localDate);

    @Query(nativeQuery = true, value = "select * from orders where is_finished = false and date = :localDate")
    List<Order> getDailyUnFinishedOrders(LocalDate localDate);

    @Query(nativeQuery = true, value = "select * from orders join answers a on a.id = orders.answer_id\n" +
            "         where\n" +
            "             a.id = '204b65cc-4c99-4d58-aed3-cb58e926e534' and\n" +
            "             is_finished = true and date = :localDate")
    List<Order> getDailyCannotFinishOrders(LocalDate localDate);

    @Query(nativeQuery = true, value = "select * from orders where date = :localDate")
    List<Order> getAllDailyOrders(LocalDate localDate);

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status " +
            "from orders O " +
            "JOIN tasks t on t.id = O.task_id " +
            "join departments d on d.id = O.department_id\n" +
            "join status s on s.id = O.status_id " +
            "where is_full = false")
    List<OrderProjection> getOrdersForProjection();

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status " +
            "from orders O " +
            "JOIN tasks t on t.id = O.task_id " +
            "join departments d on d.id = O.department_id\n" +
            "join status s on s.id = O.status_id " +
            "where is_finished = true and is_full = true and is_accept = true")
    List<OrderProjectionForClient> getFinishedOrdersForProjection();

    @Query(nativeQuery = true, value = "select ")
    List<OrderProjection> getTaskForProjection(UUID id);

    @Query(nativeQuery = true, value = "select count(*) from orders O join departments d on d.id = O.department_id\n " +
            "where O.department_id = :id")
    Integer getCountOrdersByDepartmentId(UUID id);

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status\n" +
            "            from orders O\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join departments d on d.id = O.department_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "            join users u on d.id = u.department_id\n" +
            "            where is_full = false and is_accept = false and u.id = :id")
    List<OrderProjection> getUnfinishedOrdersForProjectionByUserId(UUID id);

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status, o.end_time\n" +
            "            from orders O\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join departments d on d.id = O.department_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "            join users u on d.id = u.department_id\n" +
            "            where is_finished = true and is_full = true and u.id = :uuid")
    List<OrderProjectionForClient> getFinishedOrdersForProjectionByUserId(UUID uuid);

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status, o.end_time\n" +
            "from orders o\n" +
            "         join users u on o.department_id = u.department_id\n" +
            "         join departments d on d.id = o.department_id\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "where o.is_full = true\n" +
            "  and o.is_finished = true\n" +
            "  and u.id = :userId")
    List<OrderProjectionForClient> getMonthlyOrdersByClient(UUID userId);

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status, o.end_time\n" +
            "from orders o\n" +
            "         join users u on o.department_id = u.department_id\n" +
            "         join departments d on d.id = o.department_id\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "where o.is_full = true\n" +
            "  and o.is_finished = true\n" +
            "  and u.id = :userId")
    List<OrderProjectionForClient> getQuarterlyOrdersByClient(UUID userId);

    @Query(nativeQuery = true, value = "select cast(o.id as varchar), t.title, O.date, O.start_time, d.name as department, s.name as status, o.end_time\n" +
            "from orders o\n" +
            "         join users u on o.department_id = u.department_id\n" +
            "         join departments d on d.id = o.department_id\n" +
            "            JOIN tasks t on t.id = O.task_id\n" +
            "            join status s on s.id = O.status_id\n" +
            "where o.is_full = true\n" +
            "  and o.is_finished = true\n" +
            "  and u.id = :userId")
    List<OrderProjectionForClient> getYearlyOrdersByClient(UUID userId);

    @Query(nativeQuery = true, value = "select *\n" +
            "from orders\n" +
            "         join departments d on d.id = orders.department_id\n" +
            "         join users u on d.id = u.department_id\n" +
            " where is_full = false and is_accept = false and u.id = :id")
    Order getOrderForAcceptResult(UUID id);
}
