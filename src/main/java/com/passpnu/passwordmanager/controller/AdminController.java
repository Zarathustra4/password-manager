package com.passpnu.passwordmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/admin")
public class AdminController {

    @GetMapping
    public String adminPage(){
        return "admin/adminMenu";
    }

}
