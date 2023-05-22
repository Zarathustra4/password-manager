package com.passpnu.passwordmanager.controller;

import com.passpnu.passwordmanager.service.PasswordEntityService;
import com.passpnu.passwordmanager.util.PasswordTestAnswer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/view/guest")
@AllArgsConstructor
public class GuestController {
    private final PasswordEntityService passwordEntityService;

    @GetMapping
    public String getGuestPage(){
        return "guestPage";
    }

    @GetMapping("/generate")
    public String generatePage(Model model){
        String generatedPassword = passwordEntityService.generatePassword().getPassword();
        model.addAttribute("generatedPassword", generatedPassword);
        return "genPassword";
    }

    @GetMapping("/advice")
    public String advicePage(@RequestParam String password, Model model){
        PasswordTestAnswer passwordTestAnswer = passwordEntityService.checkPasswordStrength(password);
        List<String> descriptions = passwordTestAnswer.getDescriptions();
        boolean isStrong = passwordTestAnswer.isStrong();
        model.addAttribute("descriptions", descriptions);
        model.addAttribute("isStrong", isStrong);

        return "advice";
    }

}
