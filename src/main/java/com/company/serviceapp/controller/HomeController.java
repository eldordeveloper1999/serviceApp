package com.company.serviceapp.controller;

import com.company.serviceapp.model.Status;
import com.company.serviceapp.model.Order;
import com.company.serviceapp.projection.DepartmentProjection;
import com.company.serviceapp.projection.OrderProjection;
import com.company.serviceapp.projection.OrderProjectionForClient;
import com.company.serviceapp.repository.PrinterRepository;
import com.company.serviceapp.repository.StatusRepository;
import com.company.serviceapp.service.AdminService;
import com.company.serviceapp.service.OrderService;
import com.company.serviceapp.service.PcOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    OrderService orderService;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AdminService adminService;

    @Autowired
    PrinterRepository printerRepository;

    @Autowired
    PcOrderService pcOrderService;

    @GetMapping("/")
    public String homePage(Model model) {

        List<DepartmentProjection> departmentProjections = orderService.getDepartmentOrdersCount();

        model.addAttribute("departments", departmentProjections);

        first(model);

        List<Status> statusList = statusRepository.findAll();

        if (statusList.size() > 1) {
            model.addAttribute("status1", statusList.get(0).getName());
            model.addAttribute("status2", statusList.get(1).getName());
        } else if (statusList.size() == 1) {
            model.addAttribute("status1", statusList.get(0).getName());
        }


        return "admin/index";
    }

    private void first(Model model) throws NullPointerException {

        List<Order> dailyOrders = orderService.getDailyOrders();

        List<Order> finishedDailyOrders = orderService.getDailyFinishedOrders();

        List<Order> unFinishedDailyOrders = orderService.getDailyUnFinishedOrders();

        List<Order> cannotFinishOrders = orderService.getDailyCannotFinishOrders();

        List<OrderProjection> tasksProjections = orderService.getOrdersProjection();

        List<OrderProjection> tasksProjections2 = pcOrderService.getOrdersProjection();

        List<OrderProjection> tasksForShow = getOrdersForShow(tasksProjections);

        List<OrderProjection> tasksForShow2 = getOrdersForShow(tasksProjections2);


        if (dailyOrders.size() == 0) {
            model.addAttribute("countAllTasks", 1);
            model.addAttribute("finishedPercent", 0);
            model.addAttribute("unFinishedPercent", 0);
            model.addAttribute("cannotFinishPercent", 0);
        } else {
            double i = (finishedDailyOrders.size() / dailyOrders.size()) * 100;
            model.addAttribute("countAllTasks", dailyOrders.size());
            model.addAttribute("finishedPercent", i);
            double x = unFinishedDailyOrders.size() / dailyOrders.size() * 100;
            model.addAttribute("unFinishedPercent", x);
            model.addAttribute("cannotFinishPercent", (cannotFinishOrders.size() / dailyOrders.size() * 100));
        }

        model.addAttribute("dailyTasks", dailyOrders);
        model.addAttribute("countFinishedTasks", finishedDailyOrders.size());
        model.addAttribute("countUnFinishedTasks", unFinishedDailyOrders.size());
        model.addAttribute("countCannotFinishTasks", cannotFinishOrders.size());

        model.addAttribute("tasksForShow", tasksForShow);

        model.addAttribute("tasksForShow2", tasksForShow2);

        model.addAttribute("day", LocalDate.now());
    }

    private List<OrderProjection> getOrdersForShow(List<OrderProjection> dailyTasks) {

        if (dailyTasks.size() < 5) {
            return dailyTasks;
        }

        List<OrderProjection> tasksForShow = new ArrayList<>();

        tasksForShow.add(dailyTasks.get(0));

        tasksForShow.add(dailyTasks.get(1));

        tasksForShow.add(dailyTasks.get(2));

        tasksForShow.add(dailyTasks.get(3));

        tasksForShow.add(dailyTasks.get(4));

        return tasksForShow;
    }

    @GetMapping("/monthly/{month}")
    public String getMonthlyTasks(@PathVariable Integer month) {
        orderService.getAllMonthlyOrders(month);
        return "admin/index";
    }

    @GetMapping("/last/{day1}")
    public String getLastTasks(@PathVariable String day1, RedirectAttributes redirectAttrs) {

        Integer day = Integer.valueOf(day1);

        List<Order> tasks = orderService.getLastTask(day);

        List<Order> finishedDailyTasks = orderService.getDailyFinishedOrders(day);

        List<Order> unFinishedDailyTasks = orderService.getDailyUnFinishedOrders(day);

        List<Order> cannotFinishTasks = orderService.getDailyCannotFinishOrders(day);

        if (tasks.size() == 0) {
            redirectAttrs.addFlashAttribute("finishedPercent", 0);
            redirectAttrs.addFlashAttribute("unFinishedPercent", 0);
            redirectAttrs.addFlashAttribute("cannotFinishPercent", 0);
            redirectAttrs.addFlashAttribute("countAllTasks", 1);
        } else {
            redirectAttrs.addFlashAttribute("finishedPercent", (finishedDailyTasks.size() / tasks.size()) * 100);
            redirectAttrs.addFlashAttribute("unFinishedPercent", (unFinishedDailyTasks.size() / tasks.size()) * 100);
            redirectAttrs.addFlashAttribute("cannotFinishPercent", (cannotFinishTasks.size() / tasks.size() * 100));
            redirectAttrs.addFlashAttribute("countAllTasks", tasks.size());
        }

        redirectAttrs.addFlashAttribute("dailyTasks", tasks);
        redirectAttrs.addFlashAttribute("countAllTasks", tasks.size());
        redirectAttrs.addFlashAttribute("countFinishedTasks", finishedDailyTasks.size());
        redirectAttrs.addFlashAttribute("countUnFinishedTasks", unFinishedDailyTasks.size());
        redirectAttrs.addFlashAttribute("countCannotFinishTasks", cannotFinishTasks.size());

        redirectAttrs.addFlashAttribute("day", LocalDate.now().minusDays(day));

        List<OrderProjection> dailyTasks = orderService.getOrdersProjection();

        List<OrderProjection> tasksForShow = getOrdersForShow(dailyTasks);

        redirectAttrs.addFlashAttribute("tasksForShow", tasksForShow);

        List<DepartmentProjection> departmentProjections = orderService.getDepartmentOrdersCount();

        redirectAttrs.addFlashAttribute("departments", departmentProjections);

        return "redirect:/home";
    }


}
