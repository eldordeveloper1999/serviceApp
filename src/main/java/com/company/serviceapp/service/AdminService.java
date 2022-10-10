package com.company.serviceapp.service;

import com.company.serviceapp.dto.ProductDto;
import com.company.serviceapp.dto.ReportDto;
import com.company.serviceapp.model.Expense;
import com.company.serviceapp.model.NewProduct;
import com.company.serviceapp.model.Printer;
import com.company.serviceapp.projection.ReportProjection;
import com.company.serviceapp.repository.ExpensesRepository;
import com.company.serviceapp.repository.NewProductRepository;
import com.company.serviceapp.repository.PrinterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    PrinterRepository printerRepository;

    @Autowired
    NewProductRepository newProductRepository;

    @Autowired
    ExpensesRepository expensesRepository;


    public Printer getPrinterNameById(String printer_id) {

        return printerRepository.findById(UUID.fromString(printer_id)).get();

    }

    public void addToBase(ProductDto productDto) {

        Printer printer = printerRepository.findById(UUID.fromString(productDto.getPrinterId())).get();

        NewProduct newProduct = new NewProduct(null, productDto.getName(), productDto.getInventarNumber(), productDto.getCount(), printer);

        newProductRepository.save(newProduct);

    }

    public void getReportFile() {
        List<ReportProjection> baraban = expensesRepository.getBaraban();

        List<ReportProjection> kartrij = expensesRepository.getKartrij();

        List<ReportProjection> lezva = expensesRepository.getLezva();

        List<ReportProjection> plyonka = expensesRepository.getPlyonka();

        List<ReportProjection> toneRZapravka = expensesRepository.getToneRZapravka();

        List<ReportProjection> val = expensesRepository.getVal();

        List<ReportProjection> rakel = expensesRepository.getRakel();


        List<ReportProjection> all = new ArrayList<>();

        all.addAll(baraban);
        all.addAll(kartrij);
        all.addAll(rakel);
        all.addAll(val);
        all.addAll(plyonka);
        all.addAll(toneRZapravka);
        all.addAll(lezva);


        for (ReportProjection reportDto : all) {
            System.out.println(reportDto.getName() + ' ' + reportDto.getInventorNumber() + ' ' + reportDto.getDepartmentName() + ' ' + reportDto.getCount());
        }


    }

}
