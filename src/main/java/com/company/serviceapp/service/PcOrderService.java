package com.company.serviceapp.service;

import com.company.serviceapp.dto.*;
import com.company.serviceapp.model.*;
import com.company.serviceapp.projection.OrderProjection;
import com.company.serviceapp.projection.OrderProjectionForClient;
import com.company.serviceapp.projection.PcExpenseProjection;
import com.company.serviceapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class PcOrderService {

    @Autowired
    PcOrderRepository pcOrderRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    PcEquipmentsRepository pcEquipmentsRepository;

    @Autowired
    PcExpenseRepository pcExpenseRepository;

    @Autowired
    ClientOrderService clientOrderService;

    @Autowired
    UserRepository userRepository;

    public void receiveRequestFromClient(PcRequestDto clientRequestDto) {

        Task task = taskRepository.findById(clientRequestDto.getTaskId()).get();

        Status status = statusRepository.findById(clientRequestDto.getStatusId()).get();

        Department department = clientOrderService.getCurrentUser().getDepartment();


        PCOrder order = new PCOrder(null, task, department, status, LocalTime.now(), LocalDate.now(), null, false, false, false, clientRequestDto.getDescription(), clientRequestDto.getInventarName());
        pcOrderRepository.save(order);

    }

    public List<OrderProjection> getOrdersProjection() {

        return pcOrderRepository.getPcOrdersForProjection();

    }

    public PcOrderDto getPcOrderDto(String orderId) {

        PCOrder pcOrder = pcOrderRepository.findById(UUID.fromString(orderId)).get();

        LocalDateTime end_time = pcOrder.getEnd_time();

        LocalTime time;

        LocalDate date;

        if (end_time == null) {

            time = LocalTime.now();

            date = LocalDate.now();

        } else {

            time = LocalTime.from(end_time);

            date = LocalDate.from(end_time);

        }

        return new PcOrderDto(pcOrder.getId(), pcOrder.getTask().getTitle(), pcOrder.getTask().getDescription(), pcOrder.getDepartment().getName(),
                pcOrder.getStart_time(), pcOrder.getDate(), time, date, true, pcOrder.getInventarNumber(),
                pcOrder.getDescription(), pcOrder.getIs_accept());
    }

    public void finishOrder(PcOrderDto pcOrderDto) {

        PCOrder pcOrder = pcOrderRepository.findById(pcOrderDto.getId()).get();

        pcOrder.setDescription(pcOrderDto.getDescription());

        LocalDateTime end_time = LocalDateTime.of(pcOrderDto.getDate(), pcOrderDto.getTime());

        pcOrder.setEnd_time(end_time);

        pcOrder.setIs_finished(true);

        pcOrder.setIs_full(true);

        pcOrderRepository.save(pcOrder);

    }

    public List<OrderProjection> getUnFinishedPcTasks() {
        return pcOrderRepository.getPcOrdersForProjection();
    }

    public List<OrderProjectionForClient> getFinishedPcTasks() {

        return pcOrderRepository.getFinishedPcOrdersForProjection();
    }

    public List<OrderProjectionForClient> getMonthlyOrders() {
        List<OrderProjectionForClient> result = new ArrayList<>();

        LocalDate date = LocalDate.now();

        int month = date.getMonth().getValue();

        List<OrderProjectionForClient> monthlyOrdersByClient = pcOrderRepository.getMonthlyOrdersByClient();

        for (OrderProjectionForClient orderProjection : monthlyOrdersByClient) {
            if (orderProjection.getEnd_time().getMonth().getValue() == month) {
                result.add(orderProjection);
            }
        }

        return result;
    }

    public List<OrderProjectionForClient> getQuarterlyOrders() {
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

        List<OrderProjectionForClient> quarterlyOrdersByClient = pcOrderRepository.getQuarterlyOrdersByClient();

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

    public List<OrderProjectionForClient> getQuarterlyOrders(Integer quarter) {
        List<OrderProjectionForClient> result = new ArrayList<>();

        List<OrderProjectionForClient> quarterlyOrdersByClient = pcOrderRepository.getQuarterlyOrdersByClient();

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

    public List<OrderProjectionForClient> getYearlyOrders() {
        List<OrderProjectionForClient> result = new ArrayList<>();

        LocalDate date = LocalDate.now();

        int year = date.getYear();

        List<OrderProjectionForClient> yearlyOrdersByClient = pcOrderRepository.getYearlyOrdersByClient();

        for (OrderProjectionForClient orderProjection : yearlyOrdersByClient) {
            if (orderProjection.getEnd_time().getYear() == year) {
                result.add(orderProjection);
            }
        }

        return result;
    }

    public List<OrderProjectionForClient> getYearlyOrders(String year) {
        List<OrderProjectionForClient> result = new ArrayList<>();

        LocalDate date = LocalDate.now();

        int yearW = Integer.parseInt(year);

        List<OrderProjectionForClient> yearlyOrdersByClient = pcOrderRepository.getYearlyOrdersByClient();

        for (OrderProjectionForClient orderProjection : yearlyOrdersByClient) {
            if (orderProjection.getEnd_time().getYear() == yearW) {
                result.add(orderProjection);
            }
        }

        return result;
    }

    public Integer getCurrentQuarter() {
        return null;
    }

    public List<PcEquipment> getEquipments() {
        return pcEquipmentsRepository.findAll();
    }

    public void setPcOrderExpenses(PcExpenseDto pcExpenseDto) {

        PcEquipment equipment = pcEquipmentsRepository.getByInventor(pcExpenseDto.getConsumableProductInventor());

        Department department = departmentRepository.getByName(pcExpenseDto.getDepartment());

        User user = userRepository.findByDepartmentId(department.getId());

        PcEquipment pcEquipment = pcEquipmentsRepository.getByInventor(pcExpenseDto.getConsumableProductInventor());

        LocalDate date = LocalDate.parse(pcExpenseDto.getDateOfViolation());

        LocalDate date2 = LocalDate.parse(pcExpenseDto.getFixedDate());

        PCExpense pcExpense = new PCExpense(null, pcExpenseDto.getTitle(), pcExpenseDto.getInventorNumber(), equipment.getName(), date, date2, user,
                pcExpenseDto.getConsumableProductCount(), pcEquipment.getInventorNumber());

        pcEquipment.setCount(pcEquipment.getCount() - pcExpense.getConsumableProductCount());

        if (pcEquipment.getCount() == 0) {
            pcEquipmentsRepository.delete(pcEquipment);
        } else {
            pcEquipmentsRepository.save(pcEquipment);
        }


        pcExpenseRepository.save(pcExpense);
    }

    public void equipmentsAddToBase(PcEquipmentDto pcEquipmentDto) {
        PcEquipment pcEquipment = new PcEquipment(null, pcEquipmentDto.getName(), pcEquipmentDto.getInventorNumber(), pcEquipmentDto.getCount());
        pcEquipmentsRepository.save(pcEquipment);
    }

    public List<PCOrder> getDailyPcOrders() {
        LocalDate localDate = LocalDate.from(LocalDateTime.now());

        return pcOrderRepository.getAllDailyPcOrders(localDate);
    }

    public Integer getDailyFinishedPcOrdersCount() {
        LocalDate localDate = LocalDate.from(LocalDateTime.now());

        return pcOrderRepository.getDailyFinishedPcOrdersCount(localDate);
    }

    public Integer getDailyUnFinishedPcOrdersCount() {
        LocalDate localDate = LocalDate.from(LocalDateTime.now());

        return pcOrderRepository.getDailyUnFinishedPcOrdersCount(localDate);
    }

    public List<ReportDto> getPcReports() {
        List<ReportDto> result = new ArrayList<>();

        List<PcExpenseProjection> pcExpenses = pcExpenseRepository.getPcExpenses();

        int date = LocalDate.now().getMonthValue();

        for (PcExpenseProjection pcExpense : pcExpenses) {
            if (pcExpense.getDate().getMonthValue() == (date  - 1)) {
                result.add(new ReportDto(pcExpense.getName(), pcExpense.getInventorNumber(), pcExpense.getCountE(), pcExpense.getDepartmentName()));
            }
        }

        return result;
    }

    public PcOrderDto getPcOrderDtoByClientId(UUID id) {

        PCOrder pcOrder = pcOrderRepository.getByClientId(id);

        if (pcOrder == null) {
            return null;
        }

        LocalDateTime end_time = pcOrder.getEnd_time();

        LocalTime time;

        LocalDate date;

        if (end_time == null) {

            time = LocalTime.now();

            date = LocalDate.now();

        } else {

            time = LocalTime.from(end_time);

            date = LocalDate.from(end_time);

        }

        return new PcOrderDto(pcOrder.getId(), pcOrder.getTask().getTitle(), pcOrder.getTask().getDescription(), pcOrder.getDepartment().getName(),
                pcOrder.getStart_time(), pcOrder.getDate(), time, date, false, pcOrder.getInventarNumber(),
                pcOrder.getDescription(), pcOrder.getIs_accept());

    }
}