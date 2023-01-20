package com.company.serviceapp.controller;

import com.company.serviceapp.dto.LoginRequest;
import com.company.serviceapp.dto.UserDto;
import com.company.serviceapp.model.Department;
import com.company.serviceapp.model.User;
import com.company.serviceapp.service.AuthService;
import com.company.serviceapp.service.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.ref.Cleaner;
import java.util.List;
import java.util.Objects;

@Controller
public class AuthController {


    @Autowired
    AuthService authService;
    
    @Autowired
    ClientOrderService clientOrderService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    @PostMapping("/loginPost")
    public void Login(@ModelAttribute("loginRequest") LoginRequest loginRequest, HttpServletResponse response) throws IOException {
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    @GetMapping("/register")
    public String register(Model model, UserDto userdto) {

        List<Department> departments = authService.getAllDepartments();

        model.addAttribute("departments", departments);

        model.addAttribute("user", userdto);

        return "auth/register";
    }

    @PostMapping("/register/finish")
    public void finishRegister(@ModelAttribute("user") UserDto user, HttpServletResponse response) throws IOException {

        authService.save(user);

        response.sendRedirect("/c");

    }

}
