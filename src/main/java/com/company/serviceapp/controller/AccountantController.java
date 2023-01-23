package com.company.serviceapp.controller;

import com.company.serviceapp.dto.ClientRequestDto;
import com.company.serviceapp.dto.OrderDto;
import com.company.serviceapp.dto.PcOrderDto;
import com.company.serviceapp.dto.PcRequestDto;
import com.company.serviceapp.model.*;
import com.company.serviceapp.projection.OrderProjectionForClient;
import com.company.serviceapp.repository.*;
import com.company.serviceapp.service.AdminService;
import com.company.serviceapp.service.ClientOrderService;
import com.company.serviceapp.service.OrderService;
import com.company.serviceapp.service.PcOrderService;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/acc")
public class AccountantController {

    @Autowired
    AdminService adminService;

    @Autowired
    ClientOrderService clientOrderService;

    @Autowired
    PrinterRepository printerRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    PcOrderService pcOrderService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @GetMapping("/")
    public String home() {
        return "accountant/index";
    }

    @GetMapping("/new")
    public String addNew(Model model) {
        User currentUser = clientOrderService.getCurrentUser();

        List<Printer> printers = printerRepository.findAll();

        List<Status> statusList = statusRepository.findAll();

        List<Task> taskList = taskRepository.findAll();

        List<Task> tasks = new ArrayList<>();

        for (Task task : taskList) {
            if (!task.getTitle().equals("Zapravka kartrij")) {
                tasks.add(task);
            }
        }

        Boolean isHaveUnFinishKartrijTask = clientOrderService.isHaveUnFinishKartrijTask(currentUser.getId());
        Boolean isHaveUnFinishPcTask = clientOrderService.isHaveUnFinishPcTask(currentUser.getId());

        model.addAttribute("printers", printers);
        model.addAttribute("statuses", statusList);
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentUser", currentUser.getFullName());
        model.addAttribute("clientRequest", new ClientRequestDto());
        model.addAttribute("isHaveUnfinishKartrijTask", isHaveUnFinishKartrijTask);
        model.addAttribute("isHaveUnFinishPcTask", isHaveUnFinishPcTask);

        return "accountant/input-page";
    }

    @PostMapping("/receiveRequest")
    public String receiveRequest(@ModelAttribute("clientRequest") ClientRequestDto clientRequestDto, Model model) {
        clientOrderService.receiveRequestFromClient(clientRequestDto);
        model.addAttribute("msg", "Sizning buyurtmangiz muvaffaqiyatli jo'natildi");
        return "accountant/successPage";
    }

    @PostMapping("/receiveRequestPc")
    public String receiveRequest(@ModelAttribute("clientRequest") PcRequestDto clientRequestDto, Model model) {
        pcOrderService.receiveRequestFromClient(clientRequestDto);
        model.addAttribute("msg", "Sizning buyurtmangiz muvaffaqiyatli jo'natildi");
        return "accountant/successPage";
    }

    @GetMapping("/get-accept")
    public String getAcceptResultPage(Model model, HttpServletResponse response) {

        User currentUser = getCurrentUser();

        Order orderById = orderRepository.getOrderForAcceptResult(currentUser.getId());

        if (orderById == null) {
            model.addAttribute("msg", "Bajarilmagan buyurtma yo'q. Yangi buyurtma berishingiz mumkin!");
            return "accountant/successPage";
        }

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

        return "accountant/accept-page";
    }

    @PostMapping("/accept")
    public String acceptResult(@ModelAttribute("order") OrderDto orderDto, Model model) {
        clientOrderService.acceptResultFromAdmin();
        model.addAttribute("msg", "Amalni muvaffiqayatli yakunladingiz");
        return "accountant/successPage";
    }

    @GetMapping("/get-accept-pc")
    public String getPcAcceptPage(Model model) {

        User currentUser = getCurrentUser();

        PcOrderDto pcOrderDto = pcOrderService.getPcOrderDtoByClientId(currentUser.getId());

        if (pcOrderDto == null) {
            model.addAttribute("msg", "Bajarilmagan buyurtma yo'q. Yangi buyurtma berishingiz mumkin!");
            return "accountant/successPage";
        }

        model.addAttribute("pcOrderDto", pcOrderDto);

        return "accountant/pc-order-accept-page";
    }

    @PostMapping("/pc-accept")
    public String accepPcResult(@ModelAttribute("pcOrderDto") PcOrderDto pcOrderDto, Model model) {
        clientOrderService.acceptPcResultFromAdmin(pcOrderDto);
        model.addAttribute("msg", "Amalni muvaffiqayatli yakunladingiz");
        return "accountant/successPage";
    }

    @GetMapping("/printer")
    public String getPrinterOrders(Model model) {
        List<OrderProjectionForClient> printerTasks = orderService.getFinishedPrinterTasks();

        model.addAttribute("tasksForShow", printerTasks);

        return "accountant/";
    }

    @GetMapping("/others")
    public String getOtherOrders() {
        return "";
    }

    @GetMapping("/monthly")
    public String getMonthlyOrders() {
        return "";
    }

    @GetMapping("/quarterly")
    public String getQuarterly() {
        return "";
    }

    @GetMapping("/yearly")
    public String getYearly() {
        return "";
    }

    @GetMapping("/report-file")
    public void downloadReportFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=1111.docx");
        ByteArrayInputStream stream = adminService.getReportFile();
        IOUtils.copy(stream, response.getOutputStream());
    }

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();
        return userRepository.findByUsername(name);
    }
}
