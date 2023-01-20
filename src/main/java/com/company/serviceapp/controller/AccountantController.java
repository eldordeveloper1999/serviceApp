package com.company.serviceapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/acc")
public class AccountantController {


    @GetMapping("/")
    public String home() {
        return "accountant/index";
    }
}
