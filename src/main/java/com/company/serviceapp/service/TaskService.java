package com.company.serviceapp.service;

import com.company.serviceapp.dto.ExpenseDto;
import com.company.serviceapp.entity.*;
import com.company.serviceapp.model.*;
import com.company.serviceapp.projection.OrderProjectionForClient;
import com.company.serviceapp.repository.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {


    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ExpensesRepository expensesRepository;

    @Autowired
    PrinterRepository printerRepository;

    @Autowired
    NewProductRepository productRepository;

    @Autowired
    ClientOrderService clientOrderService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    BarabanRepository barabanRepository;

    @Autowired
    KartrijRepository kartrijRepository;

    @Autowired
    LezvaRepository lezvaRepository;

    @Autowired
    PlyonkaRepository plyonkaRepository;

    @Autowired
    RakelRepository rakelRepository;

    @Autowired
    TonerRepository tonerRepository;

    @Autowired
    ValRepository valRepository;

    @Autowired
    UserRepository userRepository;

    public Task getTaskById(String taskId) {

        return taskRepository.findById(UUID.fromString(taskId)).get();

    }

    public List<OrderProjectionForClient> getMonthlyOrders() {
        List<OrderProjectionForClient> result = new ArrayList<>();

        LocalDate date = LocalDate.now();

        int month = date.getMonth().getValue();

        List<OrderProjectionForClient> monthlyOrdersByClient = taskRepository.getMonthlyOrders();

        for (OrderProjectionForClient orderProjection : monthlyOrdersByClient) {
            if (orderProjection.getEnd_time().getMonth().getValue() == month) {
                result.add(orderProjection);
            }
        }

        return result;
    }

    public List<OrderProjectionForClient> getQuarterlyOrders() {
        List<OrderProjectionForClient> result = new ArrayList<>();

        LocalDate date = LocalDate.now();

        int month = date.getMonth().getValue();

        int quarter = switch (month) {
            case 1, 2, 3 -> 1;
            case 4, 5, 6 -> 2;
            case 7, 8, 9 -> 3;
            case 10, 11, 12 -> 4;
            default -> 0;
        };

        List<OrderProjectionForClient> quarterlyOrdersByClient = taskRepository.getQuarterlyOrders();

        for (OrderProjectionForClient orderProjection : quarterlyOrdersByClient) {
            switch (quarter) {
                case 1: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 1 || orderProjection.getEnd_time().getMonth().getValue() == 2 || orderProjection.getEnd_time().getMonth().getValue() == 3) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 2: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 4 || orderProjection.getEnd_time().getMonth().getValue() == 5 || orderProjection.getEnd_time().getMonth().getValue() == 6) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 3: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 7 || orderProjection.getEnd_time().getMonth().getValue() == 8 || orderProjection.getEnd_time().getMonth().getValue() == 9) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 4: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 10 || orderProjection.getEnd_time().getMonth().getValue() == 11 || orderProjection.getEnd_time().getMonth().getValue() == 12) {
                        result.add(orderProjection);
                    }
                }
                break;
            }
        }

        return result;
    }

    public List<OrderProjectionForClient> getQuarterlyOrders(int quarter) {
        List<OrderProjectionForClient> result = new ArrayList<>();

        List<OrderProjectionForClient> quarterlyOrdersByClient = taskRepository.getQuarterlyOrders();

        for (OrderProjectionForClient orderProjection : quarterlyOrdersByClient) {
            switch (quarter) {
                case 1: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 1 || orderProjection.getEnd_time().getMonth().getValue() == 2 || orderProjection.getEnd_time().getMonth().getValue() == 3) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 2: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 4 || orderProjection.getEnd_time().getMonth().getValue() == 5 || orderProjection.getEnd_time().getMonth().getValue() == 6) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 3: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 7 || orderProjection.getEnd_time().getMonth().getValue() == 8 || orderProjection.getEnd_time().getMonth().getValue() == 9) {
                        result.add(orderProjection);
                    }
                }
                break;
                case 4: {
                    if (orderProjection.getEnd_time().getMonth().getValue() == 10 || orderProjection.getEnd_time().getMonth().getValue() == 11 || orderProjection.getEnd_time().getMonth().getValue() == 12) {
                        result.add(orderProjection);
                    }
                }
                break;
            }
        }

        return result;
    }

    public List<OrderProjectionForClient> getYearlyOrders() {
        List<OrderProjectionForClient> result = new ArrayList<>();

        LocalDate date = LocalDate.now();

        int year = date.getYear();

        List<OrderProjectionForClient> yearlyOrdersByClient = taskRepository.getYearlyOrders();

        for (OrderProjectionForClient orderProjection : yearlyOrdersByClient) {
            if (orderProjection.getEnd_time().getYear() == year) {
                result.add(orderProjection);
            }
        }

        return result;
    }

    public List<OrderProjectionForClient> getYearlyOrders(String year) {
        List<OrderProjectionForClient> result = new ArrayList<>();

        Integer yearW = Integer.valueOf(year);

        List<OrderProjectionForClient> yearlyOrdersByClient = taskRepository.getYearlyOrders();

        for (OrderProjectionForClient orderProjection : yearlyOrdersByClient) {
            if (orderProjection.getEnd_time().getYear() == yearW) {
                result.add(orderProjection);
            }
        }

        return result;
    }

    public void calculatingExpenses(ExpenseDto expensedto) {

        Department department = departmentRepository.getByName(expensedto.getDepartment());

        User user = userRepository.findByDepartmentId(department.getId());

        List<NewProduct> products = productRepository.findAll();

        String inventarToneForZapravka = null;

        String inventarBaraban = null;

        String inventarVal = null;

        String inventarPlyonka = null;

        String inventarRakel = null;

        String inventarLezva = null;

        String inventarKartrij = null;

        if (expensedto.getToldirilganKartrijSoni() != null) {
            inventarToneForZapravka = expensedto.getInventarToneForZapravka();
            NewProduct product = productRepository.getByInventor(inventarToneForZapravka);
            Toner toner = new Toner(null, product.getName(), expensedto.getInventarToneForZapravka(), expensedto.getToldirilganKartrijSoni(), department);
            tonerRepository.save(toner);
        }

        if (expensedto.getAlmashtirilganBarabanSoni() != null) {
            inventarBaraban = expensedto.getInventarBaraban();
            NewProduct product = productRepository.getByInventor(inventarBaraban);
            Baraban baraban = new Baraban(null, product.getName(), expensedto.getInventarBaraban(), expensedto.getAlmashtirilganBarabanSoni(), department);
            barabanRepository.save(baraban);
        }

        if (expensedto.getAlmashtirilganValSoni() != null) {
            inventarVal = expensedto.getInventarVal();
            NewProduct product = productRepository.getByInventor(inventarVal);
            Val val = new Val(null, product.getName(), expensedto.getInventarVal(), expensedto.getAlmashtirilganValSoni(), department);
            valRepository.save(val);
        }

        if (expensedto.getAlmashtirilganPlyonkaSoni() != null) {
            inventarPlyonka = expensedto.getInventarPlyonka();
            NewProduct product = productRepository.getByInventor(inventarPlyonka);
            Plyonka plyonka = new Plyonka(null, product.getName(), expensedto.getInventarPlyonka(), expensedto.getAlmashtirilganPlyonkaSoni(), department);
            plyonkaRepository.save(plyonka);
        }

        if (expensedto.getAlmashtirilganRakelSoni() != null) {
            inventarRakel = expensedto.getInventarRakel();
            NewProduct product = productRepository.getByInventor(inventarRakel);
            Rakel rakel = new Rakel(null, product.getName(), expensedto.getInventarRakel(), expensedto.getAlmashtirilganRakelSoni(), department);
            rakelRepository.save(rakel);
        }

        if (expensedto.getAlmashtirilganLezvaSoni() != null) {
            inventarLezva = expensedto.getInventarLezva();
            NewProduct product = productRepository.getByInventor(inventarLezva);
            Lezva lezva = new Lezva(null, product.getName(), expensedto.getInventarLezva(), expensedto.getAlmashtirilganLezvaSoni(), department);
            lezvaRepository.save(lezva);
        }

        if (expensedto.getAlmashtirilganKartrijSoni() != null) {
            inventarKartrij = expensedto.getInventarKartrij();
            NewProduct product = productRepository.getByInventor(inventarKartrij);
            Kartrij kartrij = new Kartrij(null, product.getName(), expensedto.getInventarKartrij(), expensedto.getAlmashtirilganKartrijSoni(), department);
            kartrijRepository.save(kartrij);
        }

        for (NewProduct product : products) {
            if (inventarToneForZapravka != null) {
                if (inventarToneForZapravka.equals(product.getInventarNumber())) {
                    Integer count = product.getCount() - expensedto.getToldirilganKartrijSoni();
                    product.setCount(count);
                    if (count == 0) {
                        productRepository.delete(product);
                    } else {
                        productRepository.save(product);
                    }
                }
            } else {
                break;
            }
        }

        for (NewProduct product : products) {
            if (inventarBaraban != null) {
                if (inventarBaraban.equals(product.getInventarNumber())) {
                    Integer count = product.getCount() - expensedto.getAlmashtirilganBarabanSoni();
                    product.setCount(count);
                    if (count == 0) {
                        productRepository.delete(product);
                    } else {
                        productRepository.save(product);
                    }
                }
            }
        }

        for (NewProduct product : products) {
            if (inventarVal != null) {
                if (inventarVal.equals(product.getInventarNumber())) {
                    Integer count = product.getCount() - expensedto.getAlmashtirilganValSoni();
                    product.setCount(count);
                    if (count == 0) {
                        productRepository.delete(product);
                    } else {
                        productRepository.save(product);
                    }
                }
            }
        }

        for (NewProduct product : products) {
            if (inventarPlyonka != null) {
                if (inventarPlyonka.equals(product.getInventarNumber())) {
                    Integer count = product.getCount() - expensedto.getAlmashtirilganPlyonkaSoni();
                    product.setCount(count);
                    if (count == 0) {
                        productRepository.delete(product);
                    } else {
                        productRepository.save(product);
                    }
                }
            }
        }

        for (NewProduct product : products) {
            if (inventarRakel != null) {
                if (inventarRakel.equals(product.getInventarNumber())) {
                    Integer count = product.getCount() - expensedto.getAlmashtirilganRakelSoni();
                    product.setCount(count);
                    if (count == 0) {
                        productRepository.delete(product);
                    } else {
                        productRepository.save(product);
                    }
                }
            }
        }

        for (NewProduct product : products) {
            if (inventarLezva != null) {
                if (inventarLezva.equals(product.getInventarNumber())) {
                    Integer count = product.getCount() - expensedto.getAlmashtirilganLezvaSoni();
                    product.setCount(count);
                    if (count == 0) {
                        productRepository.delete(product);
                    } else {
                        productRepository.save(product);
                    }
                }
            }
        }

        for (NewProduct product : products) {
            if (inventarKartrij != null) {
                if (inventarKartrij.equals(product.getInventarNumber())) {
                    Integer count = product.getCount() - expensedto.getToldirilganKartrijSoni();
                    product.setCount(count);
                    if (count == 0) {
                        productRepository.delete(product);
                    } else {
                        productRepository.save(product);
                    }
                }
            }
        }


        LocalDate startDate = LocalDate.parse(expensedto.getBuzilganSana());

        LocalDate endDate = LocalDate.parse(expensedto.getTuzatilganSana());



        Expense expense = new Expense(expensedto.getTexnikaNomi(), expensedto.getInventarNumber(), department, startDate, endDate, user, expensedto.getToldirilganKartrijSoni(), inventarToneForZapravka,
                expensedto.getAlmashtirilganBarabanSoni(), inventarBaraban, expensedto.getAlmashtirilganValSoni(), inventarVal,
                expensedto.getAlmashtirilganPlyonkaSoni(), inventarPlyonka, expensedto.getAlmashtirilganRakelSoni(), inventarRakel,
                expensedto.getAlmashtirilganLezvaSoni(), inventarLezva, expensedto.getAlmashtirilganKartrijSoni(), inventarKartrij);

        expensesRepository.save(expense);
    }

    public List<NewProduct> getNewProductsByPrinter(String printerModel) {

        Printer byModel = printerRepository.getByModel(printerModel);

        return productRepository.findByPrinterId(byModel.getId());

    }

    public Integer getCurrentQuarter() {
        int value = LocalDate.now().getMonthValue();

        int res = 0;

        switch (value) {
            case 1:
            case 2:
            case 3:
                res = 1;
                break;
            case 4:
            case 5:
            case 6:
                res = 2;
                break;
            case 7:
            case 8:
            case 9:
                res = 3;
                break;
            case 10:
            case 11:
            case 12:
                res = 4;
                break;
        }

        return res;
    }
}
