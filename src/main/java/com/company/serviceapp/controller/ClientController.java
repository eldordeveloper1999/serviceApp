package com.company.serviceapp.controller;

import com.company.serviceapp.dto.ClientRequestDto;
import com.company.serviceapp.dto.OrderDto;
import com.company.serviceapp.model.*;
import com.company.serviceapp.projection.OrderProjection;
import com.company.serviceapp.projection.OrderProjectionForClient;
import com.company.serviceapp.repository.*;
import com.company.serviceapp.service.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/c")
public class ClientController {

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ClientOrderService clientOrderService;

    @Autowired
    PrinterRepository printerRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String homePage(Model model, RedirectAttributes redirectAttrs) {

        User currentUser = clientOrderService.getCurrentUser();

        List<Printer> printers = printerRepository.findAll();

        List<Status> statusList = statusRepository.findAll();

        List<Task> taskList = taskRepository.findAll();

        List<Task> tasks = new ArrayList<>();

        Task printer = new Task();

        for (Task task : taskList) {
            if (!task.getTitle().equals("Zapravka kartrij")) {
                tasks.add(task);
            } else {
                printer = task;
            }
        }

        model.addAttribute("printers", printers);
        model.addAttribute("statuses", statusList);
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentUser", currentUser.getFullName());
        model.addAttribute("clientRequest", new ClientRequestDto());

        return "client/clientPage";
    }

    @PostMapping("/receiveRequest")
    public String receiveRequest(@ModelAttribute("clientRequest") ClientRequestDto clientRequestDto, Model model) {
        clientOrderService.receiveRequestFromClient(clientRequestDto);
        model.addAttribute("msg", "Sizning buyurtmangiz muvaffaqiyatli jo'natildi");
        return "client/successPage";
    }

    @GetMapping("/order-view-unfinished")
    public String unfinishedOrderViewForClient(Model model) {

        UUID userId = getCurrentUser().getId();

        List<OrderProjection> tasksProjections = clientOrderService.getUnfinishedOrdersProjectionByUserId(userId);

        model.addAttribute("tasksForShow", tasksProjections);

        return "client/order-view-client";
    }

    @GetMapping("/order-view-finished")
    public String finishedOrderViewForClient(Model model) {

        UUID userId = getCurrentUser().getId();

        List<OrderProjectionForClient> tasksProjections = clientOrderService.getFinishedOrdersProjectionByUserId(userId);

        model.addAttribute("tasksForShow", tasksProjections);

        return "client/order-view-client-finish";
    }

    @GetMapping("/monthly")
    public String getMonthlyOrders(Model model) {

        UUID userId = getCurrentUser().getId();

        List<OrderProjectionForClient> monthlyOrders = clientOrderService.getMonthlyOrders(userId);

        model.addAttribute("tasksForShow", monthlyOrders);

        return "client/order-view-client";
    }

    @GetMapping("/kvartal")
    public String getCurrentQuarterlyOrders(Model model) {

        UUID userId = getCurrentUser().getId();

        List<OrderProjectionForClient> quarterlyOrders = clientOrderService.getQuarterlyOrders(userId);

        model.addAttribute("tasksForShow", quarterlyOrders);

        model.addAttribute("kvartal", clientOrderService.getCurrentQuarter());

        return "client/statistics/quarterly";

    }

    @GetMapping("/kvartal/{num}")
    public String getQuarterlyOrders(Model model, @PathVariable String num) {

        UUID userId = getCurrentUser().getId();

        List<OrderProjectionForClient> quarterlyOrders = clientOrderService.getQuarterlyOrders(userId, num);

        model.addAttribute("tasksForShow", quarterlyOrders);

        model.addAttribute("kvartal", num);

        return "client/statistics/quarterly";

    }

    @GetMapping("/yearly")
    public String getCurrentYearlyOrders(Model model) {

        UUID userId = getCurrentUser().getId();

        List<OrderProjectionForClient> yearlyOrders = clientOrderService.getYearlyOrders(userId);

        model.addAttribute("tasksForShow", yearlyOrders);

        model.addAttribute("year", LocalDate.now().getYear());

        return "client/statistics/yearly";

    }

    @GetMapping("/yearly/{year}")
    public String getYearlyOrders(Model model, @PathVariable String year) {

        UUID userId = getCurrentUser().getId();

        List<OrderProjectionForClient> yearlyOrders = clientOrderService.getYearlyOrders(userId, year);

        model.addAttribute("tasksForShow", yearlyOrders);

        model.addAttribute("year", year);

        return "client/statistics/yearly";

    }

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();
        return userRepository.findByUsername(name);
    }


    @GetMapping("/get-accept")
    public String getAcceptResultPage(Model model) {

        User currentUser = getCurrentUser();

        Order orderById = orderRepository.getOrderForAcceptResult(currentUser.getId());

        orderById.setIs_finished(true);

        orderRepository.save(orderById);

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
                orderById.getDate(), time, data, orderById.getIs_full(), true , orderById.getIs_accept(), orderById.getInventarNumber(), orderById.getPrinter().getModel());

        model.addAttribute("order", orderDto);

        model.addAttribute("printerModel", orderDto.getPrinterModel());

        return "client/accept-page";
    }

    @PostMapping("/accept")
    public String acceptResult(@ModelAttribute("order") OrderDto orderDto, Model model) {
        clientOrderService.acceptResultFromAdmin();
        model.addAttribute("msg", "Amalni muvaffiqayatli yakunladingiz");
        return "client/successPage";
    }
}
