package com.company.serviceapp.config.command;

import com.company.serviceapp.model.*;
import com.company.serviceapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    PrinterRepository printerRepository;

    @Autowired
    NewProductRepository newProductRepository;

    @Autowired
    PcEquipmentsRepository pcEquipmentsRepository;

    @Autowired
    ExpensesRepository expensesRepository;

    @Override
    public void run(String... args) throws Exception {

//        Department department = new Department("Direktor");
//        Department department1 = new Department("Direktor 1-o'rinbosari");
//        Department department2 = new Department("Telekommunikatsiya tarmoqlarini boshqarish bo'limi");
//        Department department3 = new Department("Telekommunikatsiya tarmoqlarini tahlil qilish bo'limi");
//        Department department4 = new Department("Axborot tizimlarini rivojlantirish bo'limi");
//        Department department5 = new Department("Tezkor boshqaruv punkti");
//        Department department6 = new Department("Normativ hujjatlar va sifat menejmenti bo'limi");
//        Department department7 = new Department("Mehnat muhofazasi bo'yicha 1-toifali muhandis");
//        Department department8 = new Department("Mobil qurilmalarni ro'yhatga olish xizmati");
//        Department department9 = new Department("Ko'chib o'tgan raqamlarni muvofiqlashtirish bo'limi");
//        Department department10 = new Department("Axborot xavfsizligi xizmati");
//        Department department11 = new Department("Kompleans nazorat bo'yicha 1-toifali mutaxassis");
//        Department department12 = new Department("Ijro intizomi va tashkiliy nazorat bo'limi");
//        Department department13 = new Department("Shartnomalar va hisoblsh bo'limi");
//        Department department14 = new Department("Buxgalteriya");
//        Department department15 = new Department("Kadrlar bo'limi");
//        Department department16 = new Department("Yuridik byuro");
//        Department department17 = new Department("Iqtisodiy tahlil va ta'riflar bo'limi");
//        Department department18 = new Department("Maxsus ishlar bo'limi");
//        Department department19 = new Department("Umumiy bo'lim");
//        Department department20 = new Department("Jismoniy va yuridik shaxslar murojaatlari bilan ishlash bo'limi");
//        Department department21 = new Department("Jamoatchilik va ommaviy axborot vositalari bilan ishlash bo'limi");
//        Department department22 = new Department("Xalqaro munosabatlar va investitsiya masalalari bo'yicha bosh mutaxassis");

//        departmentRepository.save(department);
//        departmentRepository.save(department1);
//        departmentRepository.save(department2);
//        departmentRepository.save(department3);
//        departmentRepository.save(department4);
//        departmentRepository.save(department5);
//        departmentRepository.save(department6);
//        departmentRepository.save(department7);
//        departmentRepository.save(department8);
//        departmentRepository.save(department9);
//        departmentRepository.save(department10);
//        departmentRepository.save(department11);
//        departmentRepository.save(department12);
//        departmentRepository.save(department13);
//        departmentRepository.save(department14);
//        departmentRepository.save(department15);
//        departmentRepository.save(department16);
//        departmentRepository.save(department17);
//        departmentRepository.save(department18);
//        departmentRepository.save(department19);
//        departmentRepository.save(department20);
//        departmentRepository.save(department21);
//        departmentRepository.save(department22);
//
//       userRepository.save(new User("Alex", "Telles", "alex", "1", department));
//
//
//        List<Answer> answers = new ArrayList<>(Arrays.asList(
//                new Answer(null, "Bajarildi"),
//                new Answer(null, "Bajarib bo'lmaydi")
//        ));
//
//        answerRepository.saveAll(answers);
//
//        Status shoshilinch = statusRepository.save(new Status(null, "Shoshilinch"));
//        Status shoshilinch1 = statusRepository.save(new Status(null, "Shoshilinch emas"));
//
//        statusRepository.save(shoshilinch1);
//        statusRepository.save(shoshilinch);
//
//
//        Task task = new Task(null, "Zapravka kartrij", "Some description");
//        Task task1 = new Task(null, "Kompyuter ta'mirlash", "Some description");
//
//        taskRepository.save(task);
//        taskRepository.save(task1);
//        Task task2 = new Task(null, "Internet muammosi", "Some description");
//
//        taskRepository.save(task2);
//
//        Task task3 = new Task(null, "Zoom bilan bog'liq muammo", "Some description");
//
//        taskRepository.save(task3);
//
//        List<Task> tasks = taskRepository.findAll();
//
//        List<Department> departments = departmentRepository.findAll();
//
//        List<Status> statusList = statusRepository.findAll();
//
//        List<Order> orders = new ArrayList<>(Arrays.asList(
//                new Order(null, tasks.get(0), departments.get(0), statusList.get(0), LocalTime.now(), LocalDate.now(), null, false, false, null, "32523432423"),
//                new Order(null, tasks.get(1), departments.get(2), statusList.get(1), LocalTime.now(), LocalDate.now(), null, false, false, null, "56765443353"),
//                new Order(null, tasks.get(0), departments.get(5), statusList.get(1), LocalTime.now(), LocalDate.now(), null, false, false, null, "56786546544"),
//                new Order(null, tasks.get(1), departments.get(3), statusList.get(0), LocalTime.now(), LocalDate.now(), null, false, false, null, "87677563545"),
//                new Order(null, tasks.get(0), departments.get(16), statusList.get(1), LocalTime.now(), LocalDate.now(), null, false, false, null,"13235565445"),
//                new Order(null, tasks.get(1), departments.get(9), statusList.get(0), LocalTime.now(), LocalDate.now(), null, false, false, null, "454566556345")
//        ));
//
//        orderRepository.saveAll(orders);
//
//        List<Task> taskList = new ArrayList<>(Arrays.asList(
//                new Task(null, "Windows o'rnatish", "Some description"),
//                new Task(null, "Tarmoq tizimini tekshirish", "Some description"),
//                new Task(null, "Dastur o'rnatish", "Some description"),
//                new Task(null, "KOmpyuterga texnik xizmat ko'rsatish", "Some description")
//        ));
//
//        taskRepository.saveAll(taskList);
//
//        Task task7 = new Task(null, "Boshqa muammo", "Some description");
//
//        taskRepository.save(task7);

        List<Printer> printerAll = new ArrayList<>(Arrays.asList(
            new Printer(null, "Phaser 3020"),  //0
            new Printer(null, "MF 237w"),  //1
            new Printer(null, "Canon MF 264"), //2
            new Printer(null, "Phaser 6000"),   //3
            new Printer(null, "Canon 1010")   //4
        ));

//        printerRepository.saveAll(printers);

        List<Printer> printers = printerRepository.findAll();

        List<NewProduct> products = new ArrayList<>(Arrays.asList(
                new NewProduct(null, "Ракель для Canon MF 237w", "6165", 7, printers.get(1)),
                new NewProduct(null, "Краска для Xeror", "6173", 2, printers.get(3)),
                new NewProduct(null, "Риббон Phaser 6000 с чипом", "6192", 1, printers.get(3)),
                new NewProduct(null, "Ракель для Canon MF 237W", "6455", 1, printers.get(1)),
                new NewProduct(null, "Термопленка HP LJ 1010(.)", "3182", 1, printers.get(4)),
                new NewProduct(null, "Термопленка HP LJ 1010(.)", "3200", 3, printers.get(4)),
                new NewProduct(null, "Дозирующий нож для PHASER3020BI", "6413", 9, printers.get(0)),
                new NewProduct(null, "Тонер Canon 264", "6659", 7, printers.get(2)),
                new NewProduct(null, "Ракель Canon 264", "6663", 3, printers.get(2))
        ));

//    newProductRepository.saveAll(products);

//
//        6663    	Ракель Canon 264	3	 Canon MF 264
//        6687    	Картридж	9	MF 237w
//        6723    	Тонер	1	 Canon 1010
//        6725    	Лезвие	18	MF 237w
//        6726    	Ракель для картриджа	15	MF 237w
//        6727    	Вал зарядка	16	MF 237w
//        6744    	Тонер HP LJ 1010 (100гр.) банка	27	 Canon 1010
//        6745    	Тонер HP LJ P 1005/06/07/08 (60 г)	22	MF 237w
//        6746    	Тонер HP СF 217 (60г)	30	MF 237w
//        6747    	Тонер HP LJ P 1005/06/07/08 (60 г)	5	MF 237w
//        6748    	Ракель Wiper Blade HP P1005	5	MF 237w
//        6749    	Ракель HP 1010 WB Glue Type	5	 Canon 1010
//        6751    	Ролик заряда HP 1005 SOFT PSR	12	MF 237w
//        6752    	Вал Заряда (Коротрон) PCR HP LJ CF 217/hp LaserJet Pro M102w M130 fw	15	 Canon MF 264
//        6754    	Вал Заряда (коротон )PSR Canon 052/HP CF 226 Китай	5	 Canon MF 264
//        6755    	Магнитный ролик HP 1005 Mag Roller	25	MF 237w
//        6756    	Магнитный ролик CF 217 Mag Roller	23	 Canon MF 264
//        6757    	Магнитный ролик MR HP 1010 	25	 Canon 1010
//        6758    	Магнитный ролик CF 226 Mag/Roller 	5	 Canon MF 264
//        6761    	Цилиндр (OPC Drum) HP LJ 1010 Китай	10	 Canon 1010
//        6762    	Барабан HP 226 OAS Китай	5	 Canon MF 264
//        6763    	Картридж Canon 237 w	2	MF 237w
//        6764    	Картридж Canon 051 H (2168C002)	1	 Canon MF 264
//        6765    	Картридж с барабана Canon MF264	2	 Canon MF 264
//        6766    	Картридж C-Q2612A/FX9/FX10/703 Унверсал китай	3	MF 237w
//        6767    	Картридж SLH-CF259A Китай	1	 Canon MF 264
//        6770    	XEROX-Тонер картридж голубой для Phaser-6000 c чипом (106R01631)	1	Phaser-6000
//        6771    	XEROX-Тонер картридж порпурный для Phaser-6000 c чипом (106R01632)	1	Phaser-6000
//        6772    	XEROX-Тонер картридж желтый для Phaser-6000 c чипом (106R01633)	1	Phaser-6000
//        6773    	XEROX-Тонер картридж черный для Phaser-6000 c чипом (106R01634)	1	Phaser-6000
//        6774    	XEROX-Узел термозакрепления 220 V для Phaser-6000 (126К29185)	2	Phaser-6000
//        6776    	XEROX Phaser 6000 чип для картриджа (106R01631)	2	Phaser-6000
//        6777    	XEROX Phaser 6000 чип для картриджа (106R01632)	2	Phaser-6000
//        6778    	XEROX Phaser 6000 чип для картриджа (106R01633)	2	Phaser-6000
//        6779    	XEROX Phaser 6000 чип для картриджа (106R01634)	2	Phaser-6000
//        6853    	Картридж Canon 051H оригинальтный	12	 Canon MF 264
//        6854    	Картридж для принтера	8	MF 237w
//        6857    	Фотобарабан: Фотобарабан Canon 051 (2170C001) ОРИГИНАЛЬНЫЙ	10	 Canon MF 264

        PcEquipment pcEquipment = new PcEquipment(null, "hard", "23432243", 3);

//        pcEquipmentsRepository.save(pcEquipment);

        List<Department> departments = departmentRepository.findAll();
//
//        User user = new User(null, "alex", "1", departments.get(4), "ROLE_USER");
//        User admin = new User(null, "max", "2", departments.get(6), "ROLE_ADMIN");
//        userRepository.save(user);
//        userRepository.save(admin);

//        List<Expense> expenses = expensesRepository.findAll();
//
//        for (Expense expense : expenses) {
//            System.out.println(expense);
//        }

    }
}
