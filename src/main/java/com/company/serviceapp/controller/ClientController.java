package com.company.serviceapp.controller;

import com.company.serviceapp.dto.ClientRequestDto;
import com.company.serviceapp.model.Printer;
import com.company.serviceapp.model.Status;
import com.company.serviceapp.model.Task;
import com.company.serviceapp.model.User;
import com.company.serviceapp.projection.OrderProjection;
import com.company.serviceapp.projection.OrderProjectionForClient;
import com.company.serviceapp.repository.*;
import com.company.serviceapp.service.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
    public String homePage(Model model, ClientRequestDto clientRequestDto, RedirectAttributes redirectAttrs) {

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
    public String unfinishedOrderViewForClient(Model model, HttpSession session) {

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
    public String getQuarterlyOrders(Model model) {

        UUID userId = getCurrentUser().getId();

        List<OrderProjectionForClient> quarterlyOrders = clientOrderService.getQuarterlyOrders(userId);

        model.addAttribute("tasksForShow", quarterlyOrders);

        return "client/order-view-client";

    }

    @GetMapping("/yearly")
    public String getYearlyOrders(Model model) {

        UUID userId = getCurrentUser().getId();

        List<OrderProjectionForClient> yearlyOrders = clientOrderService.getYearlyOrders(userId);

        model.addAttribute("tasksForShow", yearlyOrders);

        return "client/order-view-client";

    }

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();
        return userRepository.findByUsername(name);
    }
}