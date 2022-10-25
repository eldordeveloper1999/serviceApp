package com.company.serviceapp.service;

import com.company.serviceapp.dto.ProductDto;
import com.company.serviceapp.dto.ReportDto;
import com.company.serviceapp.entity.Kartrij;
import com.company.serviceapp.entity.Lezva;
import com.company.serviceapp.model.Expense;
import com.company.serviceapp.model.NewProduct;
import com.company.serviceapp.model.Printer;
import com.company.serviceapp.projection.*;
import com.company.serviceapp.repository.*;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class AdminService {

    @Autowired
    PrinterRepository printerRepository;

    @Autowired
    NewProductRepository newProductRepository;

    @Autowired
    ExpensesRepository expensesRepository;

    @Autowired
    TonerRepository tonerRepository;

    @Autowired
    BarabanRepository barabanRepository;

    @Autowired
    KartrijRepository kartrijRepository;

    @Autowired
    LezvaRepository lezvaRepository;

    @Autowired
    PlyonkaRepository plyonkaRepository;

    @Autowired
    ValRepository valRepository;

    @Autowired
    RakelRepository rakelRepository;

    @Autowired
    PcOrderService pcOrderService;


    public Printer getPrinterNameById(String printer_id) {

        return printerRepository.findById(UUID.fromString(printer_id)).get();

    }

    public void addToBase(ProductDto productDto) {

        Printer printer = printerRepository.findById(UUID.fromString(productDto.getPrinterId())).get();

        NewProduct newProduct = new NewProduct(null, productDto.getName(), productDto.getInventarNumber(), productDto.getCount(), printer);

        newProductRepository.save(newProduct);

    }

    public ByteArrayInputStream getReportFile() throws IOException {

        List<ReportDto> all = new ArrayList<>();

        List<ReportDto> pcReports = pcOrderService.getPcReports();

        all.addAll(pcReports);

        List<BarabanProjection> baraban = barabanRepository.getBaraban();

        for (BarabanProjection barabanProjection : baraban) {
                all.add(new ReportDto(barabanProjection.getName(), barabanProjection.getInventorNumber(), barabanProjection.getCountE(), barabanProjection.getDepartmentName()));
        }

        List<KartrijProjection> kartrij = kartrijRepository.getKartrij();

        for (KartrijProjection kartrijProjection : kartrij) {
            all.add(new ReportDto(kartrijProjection.getName(), kartrijProjection.getInventorNumber(), kartrijProjection.getCountE(), kartrijProjection.getDepartmentName()));
        }

        List<LezvaProjection> lezva = lezvaRepository.getLezva();

        for (LezvaProjection lezvaProjection : lezva) {
            all.add(new ReportDto(lezvaProjection.getName(), lezvaProjection.getInventorNumber(), lezvaProjection.getCountE(), lezvaProjection.getDepartmentName()));
        }

        List<PlyonkaProjection> plyonka = plyonkaRepository.getPlyonka();

        for (PlyonkaProjection plyonkaProjection : plyonka) {
            all.add(new ReportDto(plyonkaProjection.getName(), plyonkaProjection.getInventorNumber(), plyonkaProjection.getCountE(), plyonkaProjection.getDepartmentName()));
        }

        List<ReportProjection> toner = tonerRepository.getToneRZapravka();

        for (ReportProjection reportProjection : toner) {
            all.add(new ReportDto(reportProjection.getName(), reportProjection.getInventorNumber(), reportProjection.getCountE(), reportProjection.getDepartmentName()));
        }

        List<ValProjection> val = valRepository.getVal();

        for (ValProjection valProjection : val) {
            all.add(new ReportDto(valProjection.getName(), valProjection.getInventorNumber(), valProjection.getCountE(), valProjection.getDepartmentName()));
        }

        List<RakelProjection> rakel = rakelRepository.getRakel();

        for (RakelProjection rakelProjection : rakel) {
            all.add(new ReportDto(rakelProjection.getName(), rakelProjection.getInventorNumber(), rakelProjection.getCountE(), rakelProjection.getDepartmentName()));
        }

        return writeToWord(all);

    }


    public ByteArrayInputStream writeToWord(List<ReportDto> reports) throws IOException {

        XWPFDocument xwpfdocument = new XWPFDocument();

        File file = new File("src/main/resources/text.docx");

        FileOutputStream ostream = new FileOutputStream(file);

        XWPFParagraph para = xwpfdocument.createParagraph();
        para.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun xwpfrun = para.createRun();
        xwpfrun.setFontSize(14);
        xwpfrun.setText("УБ бошлиғи");
        xwpfrun.addBreak();
        xwpfrun.setText("Ш. Ш. Рихсиевга");
        xwpfrun.addBreak();
        xwpfrun.setText("Материалларни ҳисобдан чиқариш учун");
        xwpfrun.addBreak();

        XWPFParagraph para1 = xwpfdocument.createParagraph();
        para1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun xwpfrun1 = para1.createRun();
        xwpfrun1.addBreak();
        xwpfrun1.setFontSize(14);
        xwpfrun1.setText("2022 Август  ой учун компьютерлар ва принтерларни таъмирлашда  фойдаланилган материаллари тўғрисида ҳисобот");
        xwpfrun1.addBreak();

        XWPFTable tab = xwpfdocument.createTable();
        XWPFTableRow row = tab.getRow(0);
        row.getCell(0).setText("Sl. No.");
        row.addNewTableCell().setText("Name");
        row.addNewTableCell().setText("Count");
        row.addNewTableCell().setText("Inventar");
        row.addNewTableCell().setText("Bo'lim");
        int i = 1;
        for (ReportDto report : reports) {
            row = tab.createRow();
            row.getCell(0).setText(i++ + ".");
            row.getCell(0).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(1).setText(report.getName());
            row.getCell(1).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(2).setText(String.valueOf(report.getCount()));
            row.getCell(2).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(3).setText(report.getInventorNumber());
            row.getCell(3).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(4).setText(report.toSString());
            row.getCell(4).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        };


        XWPFParagraph para2 = xwpfdocument.createParagraph();
        para2.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun xwpfrun2 = para2.createRun();
        xwpfrun2.addBreak();
        xwpfrun2.setFontSize(14);
        xwpfrun2.setText("Аризалар илова қилинади.");
        xwpfrun2.addBreak();
        xwpfrun2.setText("АТРБ бошлиғи                                                                               Н.Мухамедов");


        xwpfdocument.write(ostream);
        ostream.close();


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        xwpfdocument.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());

    }

    public static void main(String[] args) throws Exception {

        XWPFDocument xwpfdocument = new XWPFDocument();

        File file = new File("src/main/resources/text.docx");

        FileOutputStream ostream = new FileOutputStream(file);

        XWPFParagraph para = xwpfdocument.createParagraph();
        para.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun xwpfrun = para.createRun();
        xwpfrun.setFontSize(14);
        xwpfrun.setText("УБ бошлиғи");
        xwpfrun.addBreak();
        xwpfrun.setText("Ш. Ш. Рихсиевга");
        xwpfrun.addBreak();
        xwpfrun.setText("Материалларни ҳисобдан чиқариш учун");
        xwpfrun.addBreak();

        XWPFParagraph para1 = xwpfdocument.createParagraph();
        para1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun xwpfrun1 = para1.createRun();
        xwpfrun1.addBreak();
        xwpfrun1.setFontSize(14);
        xwpfrun1.setText("2022 Август  ой учун компьютерлар ва принтерларни таъмирлашда  фойдаланилган материаллари тўғрисида ҳисобот");
        xwpfrun1.addBreak();
        xwpfrun1.addBreak();
        xwpfrun1.addBreak();
        xwpfrun1.addBreak();
        xwpfrun1.addBreak();

        XWPFParagraph para2 = xwpfdocument.createParagraph();
        para2.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun xwpfrun2 = para2.createRun();
        xwpfrun2.setFontSize(14);
        xwpfrun2.setText("Аризалар илова қилинади.");
        xwpfrun2.addBreak();
        xwpfrun2.setText("АТРБ бошлиғи                                                                               Н.Мухамедов");


        xwpfdocument.write(ostream);
        ostream.close();
    }

}
