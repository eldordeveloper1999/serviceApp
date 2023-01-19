package com.company.serviceapp.aspect;


import com.company.serviceapp.model.PCOrder;
import com.company.serviceapp.model.Printer;
import com.company.serviceapp.projection.OrderProjection;
import com.company.serviceapp.service.AspectService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class AppAspectJ {

    @Autowired
    AspectService aspectService;

    @Pointcut("execution(* com.company.serviceapp.controller.*.*(..))")
    private void forAppFlow() {
    }

    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof Model) {

                List<OrderProjection> taskProjections = aspectService.getTasks();

                List<Printer> printers = aspectService.getPrinters();

                List<Printer> printersS = new ArrayList<>(printers);
                printersS.add(new Printer(null, "Boshqa mahsulotlar"));

                List<OrderProjection> pcOrders = aspectService.getPcOrders();

                ((Model) arg).addAttribute("notifications_count", taskProjections.size() + pcOrders.size());
                ((Model) arg).addAttribute("printers", printers);
                ((Model) arg).addAttribute("printersS", printersS);

               // ((Model) arg).addAttribute("notifications", taskProjections);
            }
        }
    }

}
