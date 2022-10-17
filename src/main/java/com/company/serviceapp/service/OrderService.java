package com.company.serviceapp.service;

import com.company.serviceapp.dto.ClientRequestDto;
import com.company.serviceapp.dto.OrderDto;
import com.company.serviceapp.model.*;
import com.company.serviceapp.projection.DepartmentProjection;
import com.company.serviceapp.projection.OrderProjection;
import com.company.serviceapp.projection.OrderProjectionForClient;
import com.company.serviceapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PrinterRepository printerRepository;


//    public List<Order> getUnfinishedOrdersByDepartment() {
//
//        UUID userId = clientOrderService.getCurrentUser().getId();
//
//        User user = userRepository.findById(userId).get();
//
//        UUID departmentId = user.getDepartment().getId();
//
//        return orderRepository.findByDepartmentId(departmentId);
//    }

//    public List<Order> getFinishedOrdersByDepartment() {
//
//        User user = userRepository.findById(userId).get();
//
//        UUID departmentId = user.getDepartment().getId();
//
//        return orderRepository.findByDepartmentIdFinishedOrders(departmentId);
//    }

    public List<Order> getAllMonthlyOrders(Integer month) {
        List<Order> tasks = new ArrayList<>();

        return tasks;
    }

    public List<Order> getDailyOrders() {

        LocalDate localDate = LocalDate.from(LocalDateTime.now());

        return orderRepository.getAllDailyOrders(localDate);
    }

    public List<Order> getLastTask(int day) {

        LocalDate localDate = LocalDate.from(LocalDateTime.now());

        return orderRepository.getLastOrders(Date.valueOf(localDate.minusDays(day)));

    }

    public List<Order> getDailyFinishedOrders() {

        LocalDate localDate = LocalDate.from(LocalDateTime.now());

        return orderRepository.getDailyFinishedOrders(localDate);

    }

    public List<Order> getDailyUnFinishedOrders() {

        LocalDate localDate = LocalDate.from(LocalDateTime.now());

        return orderRepository.getDailyUnFinishedOrders(localDate);

    }

    public List<Order> getDailyCannotFinishOrders() {

        LocalDate localDate = LocalDate.from(LocalDateTime.now());

        return orderRepository.getDailyCannotFinishOrders(localDate);

    }

    public List<Order> getDailyFinishedOrders(int day) {

        LocalDate localDate = LocalDate.from(LocalDateTime.now());

        return orderRepository.getDailyFinishedOrders(localDate.minusDays(day));


    }

    public List<Order> getDailyUnFinishedOrders(int day) {

        LocalDate localDate = LocalDate.from(LocalDateTime.now());

        return orderRepository.getDailyUnFinishedOrders(localDate.minusDays(day));

    }

    public List<Order> getDailyCannotFinishOrders(int day) {

        LocalDate localDate = LocalDate.from(LocalDateTime.now());

        return orderRepository.getDailyCannotFinishOrders(localDate.minusDays(day));

    }

    public List<OrderProjection> getOrdersProjection() {

        return orderRepository.getOrdersForProjection();

    }

    public List<OrderProjection> getTaskProjection(UUID uuid) {

        return orderRepository.getTaskForProjection(uuid);

    }

    public List<DepartmentProjection> getDepartmentOrdersCount() {

        return departmentRepository.getDepartmentOrdersCount();
    }

    public Order getOrderById(String orderId) {

        return orderRepository.findById(UUID.fromString(orderId)).get();

    }

    public void endTask(OrderDto orderDto) {

        Department department = departmentRepository.getByName(orderDto.getDepartment());

        Status status = statusRepository.getStatusByOrderId(orderDto.getId());

        LocalDateTime end_time = LocalDateTime.of(orderDto.getDate(), orderDto.getTime());

        Answer answer = answerRepository.getAnswerByName(orderDto.getAnswerName());

        Task task = taskRepository.getTaskByOrderId(orderDto.getId());

        Printer printer = printerRepository.getByModel(orderDto.getPrinterModel());

        Order order = new Order(orderDto.getId(), task, department, status, orderDto.getStart_time(), orderDto.getDate(), end_time, true, true, true, answer, orderDto.getInventarNumber(), printer);

        orderRepository.save(order);
    }

    public List<OrderProjection> getUnFinishedPrinterTasks() {
        return orderRepository.getOrdersForProjection();
    }

    public List<OrderProjectionForClient> getFinishedPrinterTasks() {
        return orderRepository.getFinishedOrdersForProjection();
    }
}

