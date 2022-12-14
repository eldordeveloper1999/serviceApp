package com.company.serviceapp.service;

import com.company.serviceapp.model.PCOrder;
import com.company.serviceapp.model.Printer;
import com.company.serviceapp.projection.OrderProjection;
import com.company.serviceapp.repository.OrderRepository;
import com.company.serviceapp.repository.PcOrderRepository;
import com.company.serviceapp.repository.PrinterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AspectService {

    @Autowired
    OrderRepository taskRepository;

    @Autowired
    PrinterRepository printerRepository;

    @Autowired
    PcOrderService pcOrderService;

    public List<OrderProjection> getTasks() {
        return taskRepository.getOrdersForProjection();
    }

    public List<Printer> getPrinters() {
        return printerRepository.findAll();
    }

    public List<OrderProjection> getPcOrders() {
        return pcOrderService.getUnFinishedPcTasks();
    }

}
