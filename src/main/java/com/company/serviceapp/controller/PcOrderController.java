package com.company.serviceapp.controller;

import com.company.serviceapp.dto.*;
import com.company.serviceapp.model.*;
import com.company.serviceapp.projection.OrderProjectionForClient;
import com.company.serviceapp.repository.AnswerRepository;
import com.company.serviceapp.service.OrderService;
import com.company.serviceapp.service.PcOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/pc")
public class PcOrderController {

    @Autowired
    PcOrderService pcOrderService;

    @Autowired
    AnswerRepository answerRepository;

    @PostMapping("/receiveRequest")
    public String receiveRequest(@ModelAttribute("clientRequest") PcRequestDto clientRequestDto, Model model) {
        pcOrderService.receiveRequestFromClient(clientRequestDto);
        model.addAttribute("msg", "Sizning buyurtmangiz muvaffaqiyatli jo'natildi");
        return "client/successPage";
    }

    @GetMapping("/finishTask/{orderId}")
    public String finishTask(Model model, @PathVariable String orderId) {

        PcOrderDto pcOrderDto = pcOrderService.getPcOrderDto(orderId);

        List<Answer> answers = answerRepository.findAll();

        model.addAttribute("pcOrderDto", pcOrderDto);

        model.addAttribute("answers", answers);

        return "admin/answer-pc-page";

    }

    @PostMapping("/continue")
    public void endTask(@ModelAttribute("pcOrderDto") PcOrderDto pcOrderDto, HttpServletResponse response) throws IOException {
        pcOrderService.finishOrder(pcOrderDto);
        response.sendRedirect("/");
    }

    @GetMapping("/expense/{orderId}")
    public String expensePage(@PathVariable String orderId, Model model) {
        List<PcEquipment> equipments = pcOrderService.getEquipments();
        PcOrderDto pcOrderDto = pcOrderService.getPcOrderDto(orderId);
        PcExpenseDto pcExpenseDto = new PcExpenseDto();
        pcExpenseDto.setTitle(pcOrderDto.getTitle());
        pcExpenseDto.setInventorNumber(pcOrderDto.getInventarNumber());
        model.addAttribute("pcOrderDto", pcOrderDto);
        model.addAttribute("equipments", equipments);
        model.addAttribute("pcExpense", pcExpenseDto);
        model.addAttribute("orderId", orderId);
        return "admin/pc-expense-page";
    }

    @PostMapping("/get-expense/{orderId}")
    public void getExpenses(@PathVariable String orderId, @ModelAttribute("pcExpense") PcExpenseDto pcExpenseDto, HttpServletResponse response) throws IOException {
        pcOrderService.setPcOrderExpenses(pcExpenseDto);
        response.sendRedirect("/pc/finishTask/" + orderId + "");
    }

    @GetMapping("/pc-monthly")
    public String getMonthlyOrders(Model model) {

        List<OrderProjectionForClient> monthlyOrders = pcOrderService.getMonthlyOrders();
        model.addAttribute("tasksForShow", monthlyOrders);
        return "admin/statistics/pc-task-monthly";
    }

    @GetMapping("/pc-quarterly")
    public String getCurrentQuarterlyOrders(Model model) {

        List<OrderProjectionForClient> quarterlyOrders = pcOrderService.getQuarterlyOrders();

        model.addAttribute("tasksForShow", quarterlyOrders);

        Integer currentQuarter = pcOrderService.getCurrentQuarter();
        model.addAttribute("quarter", currentQuarter);

        return "admin/statistics/pc-task-quarterly";

    }

    @GetMapping("/pc-quarterly/{num}")
    public String getQuarterlyOrders(Model model, @PathVariable Integer num) {

        List<OrderProjectionForClient> quarterlyOrders = pcOrderService.getQuarterlyOrders(num);

        model.addAttribute("tasksForShow", quarterlyOrders);
        model.addAttribute("quarter", num);

        return "admin/statistics/pc-task-quarterly";

    }

    @GetMapping("/pc-yearly")
    public String getYearlyOrders(Model model) {

        List<OrderProjectionForClient> yearlyOrders = pcOrderService.getYearlyOrders();

        model.addAttribute("tasksForShow", yearlyOrders);

        model.addAttribute("year", LocalDate.now().getYear());

        return "admin/statistics/pc-task-yearly";

    }

    @GetMapping("/pc-yearly/{year}")
    public String getYearlyOrders(Model model, @PathVariable String year) {

        List<OrderProjectionForClient> yearlyOrders = pcOrderService.getYearlyOrders(year);

        model.addAttribute("tasksForShow", yearlyOrders);

        model.addAttribute("year", year);

        return "admin/statistics/pc-task-yearly";

    }

    @GetMapping(value = "/pc-equipment")
    public String getEquipmentAddToBasePage(Model model) {
        model.addAttribute("equipment", new PcEquipmentDto());
        return "admin/restore-equipment-page";
    }

    @PostMapping(value = "/restore-equipment")
    public String equipmentAddToBase(@ModelAttribute("equipment") PcEquipmentDto pcEquipmentDto, Model model) {
        pcOrderService.equipmentsAddToBase(pcEquipmentDto);
        model.addAttribute("msg", "Mahsulot muvaffaqiyatli bazaga qo'shildi");
        return "admin/success-page";
    }

}
