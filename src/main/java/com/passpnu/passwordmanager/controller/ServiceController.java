package com.passpnu.passwordmanager.controller;

import com.passpnu.passwordmanager.dto.service.ServiceDto;
import com.passpnu.passwordmanager.service.ServiceEntityService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.NameNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/view/services")
@AllArgsConstructor
public class ServiceController {
    private final ServiceEntityService serviceEntityService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/list")
    public String servicesListPage(Model model){
        List<ServiceDto> serviceDtoList = serviceEntityService.getServiceList();
        model.addAttribute("serviceList", serviceDtoList);
        return "user/serviceListPage";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/change")
    public String changeService(
            ServiceDto serviceDto
            ) throws NameNotFoundException {
        String message;
        if(serviceEntityService.existsByDomain(serviceDto)){
            serviceEntityService.putService(serviceDto);
            message = "Service was changed";
        }else{
            serviceEntityService.postService(serviceDto);
            message = "Service was created";
        }

        return "redirect:/view/passwords/response?message=%s".formatted(message);
    }
}
