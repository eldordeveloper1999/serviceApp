package com.company.serviceapp.service;

import com.company.serviceapp.dto.ClientRequestDto;
import com.company.serviceapp.model.*;
import com.company.serviceapp.projection.OrderProjection;
import com.company.serviceapp.projection.OrderProjectionForClient;
import com.company.serviceapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientOrderService {


    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PrinterRepository printerRepository;

    @Autowired
    PcOrderRepository pcOrderRepository;


    public void receiveRequestFromClient(ClientRequestDto clientRequestDto) {

        List<Task> taskList = taskRepository.findAll();

        List<Task> tasks = new ArrayList<>();

        Task printer1 = new Task();

        for (Task task : taskList) {
            if (!task.getTitle().equals("Zapravka kartrij")) {
                tasks.add(task);
            } else {
                printer1 = task;
            }
        }

        Status status = statusRepository.findById(clientRequestDto.getStatusId()).get();

        UUID userId = getCurrentUser().getId();

        Department department = departmentRepository.getByUserId(userId);

        Printer printer = printerRepository.findById(clientRequestDto.getPrinterId()).get();
        Order order = new Order(null, printer1, department, status, LocalTime.now(), LocalDate.now(), null, false, false, false, null, clientRequestDto.getInventarName(), printer);
        orderRepository.save(order);

    }

    public List<OrderProjection> getUnfinishedOrdersProjectionByUserId(UUID userId) {
        return orderRepository.getUnfinishedOrdersForProjectionByUserId(userId);
    }

    public List<OrderProjectionForClient> getFinishedOrdersProjectionByUserId(UUID userId) {
        return orderRepository.getFinishedOrdersForProjectionByUserId(userId);
    }

    public List<OrderProjectionForClient> getMonthlyOrders(UUID userId) {

        List<OrderProjectionForClient> result = new ArrayList<>();

        LocalDate date = LocalDate.now();

        int month = date.getMonth().getValue();

        List<OrderProjectionForClient> monthlyOrdersByClient = orderRepository.getMonthlyOrdersByClient(userId);

        for (OrderProjectionForClient orderProjection : monthlyOrdersByClient) {
            if (orderProjection.getEnd_time().getMonth().getValue() == month) {
                result.add(orderProjection);
            }
        }

        return result;
    }

    public List<OrderProjectionForClient> getQuarterlyOrders(UUID userId) {

        List<OrderProjectionForClient> result = new ArrayList<>();

        LocalDate date = LocalDate.now();

        int month = date.getMonth().getValue();

        int quarter = switch (month) {
            case 1, 2, 3 -> 1;
            case 4, 5, 6 -> 2;
            case 7, 8, 9 -> 3;
            case 10, 11, 12 -> 4;
            default -> 0;
        };

        List<OrderProjectionForClient> quarterlyOrdersByClient = orderRepository.getQuarterlyOrdersByClient(userId);

        for (OrderProjectionForClient orderProjection : quarterlyOrdersByClient) {
            switch (quarter) {
                case 1: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 1 || orderProjection.getEnd_time().getMonth().getValue() == 2 || orderProjection.getEnd_time().getMonth().getValue() == 3) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 2: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 4 || orderProjection.getEnd_time().getMonth().getValue() == 5 || orderProjection.getEnd_time().getMonth().getValue() == 6) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 3: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 7 || orderProjection.getEnd_time().getMonth().getValue() == 8 || orderProjection.getEnd_time().getMonth().getValue() == 9) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 4: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 10 || orderProjection.getEnd_time().getMonth().getValue() == 11 || orderProjection.getEnd_time().getMonth().getValue() == 12) {
                        result.add(orderProjection);
                    }
                }
                break;
            }
        }

        return result;
    }

    public List<OrderProjectionForClient> getQuarterlyOrders(UUID userId, String kvartal) {

        List<OrderProjectionForClient> result = new ArrayList<>();

        int quarter = Integer.parseInt(kvartal);

        List<OrderProjectionForClient> quarterlyOrdersByClient = orderRepository.getQuarterlyOrdersByClient(userId);

        for (OrderProjectionForClient orderProjection : quarterlyOrdersByClient) {
            switch (quarter) {
                case 1: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 1 || orderProjection.getEnd_time().getMonth().getValue() == 2 || orderProjection.getEnd_time().getMonth().getValue() == 3) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 2: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 4 || orderProjection.getEnd_time().getMonth().getValue() == 5 || orderProjection.getEnd_time().getMonth().getValue() == 6) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 3: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 7 || orderProjection.getEnd_time().getMonth().getValue() == 8 || orderProjection.getEnd_time().getMonth().getValue() == 9) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 4: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 10 || orderProjection.getEnd_time().getMonth().getValue() == 11 || orderProjection.getEnd_time().getMonth().getValue() == 12) {
                        result.add(orderProjection);
                    }
                }
                break;
            }
        }

        return result;
    }

    public List<OrderProjectionForClient> getYearlyOrders(UUID userId) {

        List<OrderProjectionForClient> result = new ArrayList<>();

        LocalDate date = LocalDate.now();

        int year = date.getYear();

        List<OrderProjectionForClient> yearlyOrdersByClient = orderRepository.getYearlyOrdersByClient(userId);

        for (OrderProjectionForClient orderProjection : yearlyOrdersByClient) {
            if (orderProjection.getEnd_time().getYear() == year) {
                result.add(orderProjection);
            }
        }

        return result;
    }

    public List<OrderProjectionForClient> getYearlyOrders(UUID userId, String y) {

        List<OrderProjectionForClient> result = new ArrayList<>();

        int year = Integer.parseInt(y);

        List<OrderProjectionForClient> yearlyOrdersByClient = orderRepository.getYearlyOrdersByClient(userId);

        for (OrderProjectionForClient orderProjection : yearlyOrdersByClient) {
            if (orderProjection.getEnd_time().getYear() == year) {
                result.add(orderProjection);
            }
        }

        return result;
    }

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();
        return userRepository.findByUsername(name);
    }

    public Integer getCurrentQuarter() {
        int value = LocalDate.now().getMonthValue();

        int res = 0;

        switch (value) {
            case 1:
            case 2:
            case 3:
                res = 1;
                break;
            case 4:
            case 5:
            case 6:
                res = 2;
                break;
            case 7:
            case 8:
            case 9:
                res = 3;
                break;
            case 10:
            case 11:
            case 12:
                res = 4;
                break;
        }

        return res;
    }

    public void acceptResultFromAdmin() {
        User currentUser = getCurrentUser();
        Order result = orderRepository.getOrderForAcceptResult(currentUser.getId());
        result.setIs_accept(true);
        orderRepository.save(result);
    }

    public Boolean isHaveUnFinishKartrijTask(UUID id) {

        List<OrderProjection> unfinishedOrdersForProjectionByUserId = orderRepository.getUnfinishedOrdersForProjectionByUserId(id);

        if (unfinishedOrdersForProjectionByUserId.size() > 0) {
            return false;
        }

        return true;
    }

    public Boolean isHaveUnFinishPcTask(UUID id) {

        List<PCOrder> pcOrders = pcOrderRepository.getUnfinishedTasks(id);
return true;
    }
}
