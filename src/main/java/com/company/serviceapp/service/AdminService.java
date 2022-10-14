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
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

    public ByteArrayInputStream getReportFile() {
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

        return writeToWord(all);

    }


    public ByteArrayInputStream writeToWord(List<ReportProjection> reports) {
        XWPFDocument document= new XWPFDocument();
        try(FileOutputStream out = new FileOutputStream(new File("1111.docx"))){
            XWPFTable tab = document.createTable();
            XWPFTableRow row = tab.getRow(0);
            row.getCell(0).setText("Sl. No.");
            row.addNewTableCell().setText("Name");
            row.addNewTableCell().setText("Count");
            row.addNewTableCell().setText("Inventar");
            row.addNewTableCell().setText("Bo'lim");
            int i = 1;
            for (ReportProjection report : reports) {
                row = tab.createRow();
                row.getCell(0).setText(i++ + ".");
                row.getCell(1).setText(report.getName());
                row.getCell(2).setText(String.valueOf(report.getCount()));
                row.getCell(3).setText(report.getInventorNumber());
                row.getCell(4).setText(report.getDepartmentName());
            }
            document.write(out);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());

        }catch(Exception e) {
            System.out.println(e);
        }

        return null;
    }


}
