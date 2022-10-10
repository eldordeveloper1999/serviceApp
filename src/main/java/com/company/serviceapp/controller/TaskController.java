package com.company.serviceapp.controller;

import com.company.serviceapp.dto.ExpenseDto;
import com.company.serviceapp.dto.OrderDto;
import com.company.serviceapp.dto.ProductDto;
import com.company.serviceapp.model.*;
import com.company.serviceapp.projection.OrderProjection;
import com.company.serviceapp.projection.OrderProjectionForClient;
import com.company.serviceapp.repository.AnswerRepository;
import com.company.serviceapp.service.AdminService;
import com.company.serviceapp.service.OrderService;
import com.company.serviceapp.service.PcOrderService;
import com.company.serviceapp.service.TaskService;
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
@RequestMapping("/task")
public class TaskController {

    @Autowired
    OrderService orderService;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    AdminService adminService;

    @Autowired
    PcOrderService pcOrderService;

    @GetMapping("/workplace")
    public String homePage(Model model) {

        List<OrderProjection> tasksProjections = orderService.getOrdersProjection();

        model.addAttribute("tasksForShow", tasksProjections);

        return "admin/workplace";
    }

    @GetMapping("/finishTask/{orderId}")
    public String finishTask(Model model, @PathVariable String orderId) {

        Order orderById = orderService.getOrderById(orderId);

        List<Answer> answers = answerRepository.findAll();

        LocalDateTime end_time = orderById.getEnd_time();

        LocalTime time;

        LocalDate data;

        if (end_time == null) {

            time = LocalTime.now();

            data = LocalDate.now();

        } else {

            time = LocalTime.from(end_time);

            data = LocalDate.from(end_time);

        }
        OrderDto orderDto = new OrderDto(orderById.getId(), orderById.getTask().getTitle(), orderById.getTask().getDescription(),
                orderById.getDepartment().getName(), orderById.getStart_time(),
                orderById.getDate(), time, data, true, orderById.getInventarNumber(), orderById.getPrinter().getModel());

        model.addAttribute("order", orderDto);

        model.addAttribute("printerModel", orderDto.getPrinterModel());

        model.addAttribute("answers", answers);

        return "admin/answer";
    }

    @PostMapping("/continue")
    public String endTask(@ModelAttribute("order") OrderDto orderDto, Model model) throws IOException {

        orderService.endTask(orderDto);

        ExpenseDto expenseDto = new ExpenseDto();

        expenseDto.setTexnikaNomi(orderDto.getPrinterModel());
        expenseDto.setInventarNumber(orderDto.getInventarNumber());

        List<NewProduct> products = taskService.getNewProductsByPrinter(orderDto.getPrinterModel());

        products.add(new NewProduct(null, "select", null, 0, null));

        model.addAttribute("expense", expenseDto);

        model.addAttribute("products", products);

        return "admin/input-expenses-page";
    }

    @PostMapping("/finish")
    public String calculatingExpenses(@ModelAttribute("expense") ExpenseDto expense, Model model) {

        taskService.calculatingExpenses(expense);

        model.addAttribute("msg", "Buyurtma muvaffaqiyatli yakunlandi");

        return "admin/finish-success-page";
    }

    @GetMapping("/monthly")
    public String getCurrentMonthlyOrders(Model model) {

        List<String> months = new ArrayList<>(Arrays.asList("Yanvar", "Fevral", "Mart", "Aprel", "May", "Iyun", "Iyul", "Avgust", "Sentabr", "Oktabr", "Noyabr", "Decabr"));
        List<OrderProjectionForClient> monthlyOrders = taskService.getMonthlyOrders();
        model.addAttribute("tasksForShow", monthlyOrders);
        model.addAttribute("months", months);

        return "admin/statistics/task-monthly";
    }

    @GetMapping("/kvartal")
    public String getCurrentQuarterlyOrders(Model model) {

        List<OrderProjectionForClient> quarterlyOrders = taskService.getQuarterlyOrders();

        Integer currentQuarter = taskService.getCurrentQuarter();

        model.addAttribute("tasksForShow", quarterlyOrders);
        model.addAttribute("quarter", currentQuarter);
        return "admin/statistics/task-quarterly";

    }

    @GetMapping("/quarterly/{num}")
    public String getQuarterlyOrders(Model model, @PathVariable Integer num) {

        List<OrderProjectionForClient> quarterlyOrders = taskService.getQuarterlyOrders(num);

        model.addAttribute("tasksForShow", quarterlyOrders);
        model.addAttribute("quarter", num);
        return "admin/statistics/task-quarterly";

    }

    @GetMapping("/yearly")
    public String getYearlyOrders(Model model) {

        List<OrderProjectionForClient> yearlyOrders = taskService.getYearlyOrders();

        model.addAttribute("tasksForShow", yearlyOrders);

        return "admin/statistics/task-yearly";

    }

    @GetMapping("/printer/{printer_id}")
    public String getPrinterPage(@PathVariable String printer_id, Model model, ProductDto productDto) {

        Printer printer = adminService.getPrinterNameById(printer_id);

        productDto.setPrinterId(printer_id);

        model.addAttribute("product", productDto);
        model.addAttribute("printerId", printer_id);
        model.addAttribute("printerName", printer.getModel());

        return "admin/restore-base-page";
    }

    @PostMapping("/restore")
    public void addToBase(@ModelAttribute("product") ProductDto productDto, HttpServletResponse response) throws IOException {

        adminService.addToBase(productDto);

        //response.sendRedirect("/task/printer/{" + productDto.getPrinterId() + "}");
    }

    @GetMapping(value = "/new")
    public String getUnfinishedTasksByDepartment(Model model) {
        List<OrderProjection> printerTasks = orderService.getUnFinishedPrinterTasks();

        model.addAttribute("tasksForShow", printerTasks);

        return "admin/workplace";
    }

    @GetMapping(value = "/pc-task")
    public String getUnfinishedPcTasksByDepartment(Model model) {
        List<OrderProjection> pcTasks = pcOrderService.getUnFinishedPcTasks();

        model.addAttribute("tasksForShow", pcTasks);
        return "admin/workplace-pc-new-task";
    }

    @GetMapping(value = "/finished")
    public String getFinishedTasksByDepartment(Model model) {
        List<OrderProjectionForClient> printerTasks = orderService.getFinishedPrinterTasks();

        model.addAttribute("tasksForShow", printerTasks);

        return "admin/workplace-for-finished";
    }

    @GetMapping(value = "/pc-task-f")
    public String getFinishedPcTasksByDepartment(Model model) {

        List<OrderProjectionForClient> pcTasks = pcOrderService.getFinishedPcTasks();

        model.addAttribute("tasksForShow", pcTasks);
        return "admin/workplace-for-finished";
    }

    @GetMapping(value = "/report-file")
    public void getReportFile() {
        adminService.getReportFile();
    }
}
