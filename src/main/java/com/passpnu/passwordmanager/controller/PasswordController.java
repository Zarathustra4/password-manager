package com.passpnu.passwordmanager.controller;

import com.passpnu.passwordmanager.dto.AnalysisDto;
import com.passpnu.passwordmanager.dto.password.PasswordServiceIdDto;
import com.passpnu.passwordmanager.dto.password.PasswordWebChangeDto;
import com.passpnu.passwordmanager.dto.password.PasswordWebDto;
import com.passpnu.passwordmanager.dto.service.ServiceDto;
import com.passpnu.passwordmanager.dto.user.AuthUserDetailsDto;
import com.passpnu.passwordmanager.exception.EncryptionException;
import com.passpnu.passwordmanager.exception.PasswordServiceMappingException;
import com.passpnu.passwordmanager.exception.ServiceOccupiedException;
import com.passpnu.passwordmanager.service.PasswordEntityService;
import com.passpnu.passwordmanager.service.ServiceEntityService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.NameNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/view/passwords")
@AllArgsConstructor
public class PasswordController {
    private final PasswordEntityService passwordEntityService;
    private final ServiceEntityService serviceEntityService;
    private static final String REDIRECT_URL = "redirect:/view/passwords/response?message=%s";

    @PreAuthorize("hasRole('ROLE_USER')")

    @GetMapping("/menu")
    public String menuPage(){
        return "user/userMenu";
    }

    @PreAuthorize("hasRole('ROLE_USER')")

    @GetMapping("/response")
    public String response(@RequestParam String message, Model model){
        model.addAttribute("response", message);
        return "user/responsePage";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-password")
    public String getPassword(
            @RequestParam String serviceDomain,
            @AuthenticationPrincipal AuthUserDetailsDto user,
            Model model){

        Long id;
        try {
            id = serviceEntityService.getIdByDomain(serviceDomain);
            String password = passwordEntityService.getPassword(id, user).getPassword();
            model.addAttribute("password", password);
        }

        catch (NameNotFoundException | EncryptionException ex){
            return "error";
        }

        return "user/getPassword";
    }

    @PreAuthorize("hasRole('ROLE_USER')")

    @PostMapping("/store")
    public String storePassword(
            @AuthenticationPrincipal AuthUserDetailsDto user,
            PasswordWebDto passwordWebDto
    ) throws NameNotFoundException, ServiceOccupiedException, EncryptionException {
        String serviceDomain = passwordWebDto.getServiceDomain();
        if(!serviceEntityService.existsByDomain(serviceDomain)){
            ServiceDto serviceDto =
                    ServiceDto.builder()
                            .title(passwordWebDto.getServiceName())
                            .domain(serviceDomain)
                            .description(passwordWebDto.getServiceName())
            .build();

            serviceEntityService.postService(serviceDto);
        }
        Long serviceId = serviceEntityService.getIdByDomain(serviceDomain);

        PasswordServiceIdDto passwordServiceIdDto =
                PasswordServiceIdDto.builder()
                .password(passwordWebDto.getPassword())
                .serviceId(serviceId)
                .build();

        passwordEntityService.savePassword(passwordServiceIdDto, user);

        String message = "Password was stored";

        return REDIRECT_URL.formatted(message);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/change")
    public String changePassword(
            @AuthenticationPrincipal AuthUserDetailsDto user,
            PasswordWebChangeDto passwordDto
    ) throws NameNotFoundException, EncryptionException, PasswordServiceMappingException {
        String serviceDomain = passwordDto.getServiceDomain();
        if(!serviceEntityService.existsByDomain(serviceDomain)){
            return "redirect:/error";
        }
        Long serviceId = serviceEntityService.getIdByDomain(serviceDomain);

        PasswordServiceIdDto passwordServiceIdDto =
                PasswordServiceIdDto.builder()
                        .password(passwordDto.getPassword())
                        .serviceId(serviceId)
                        .build();

        passwordEntityService.changePassword(passwordServiceIdDto, user);

        String message = "Password was changed";

        return REDIRECT_URL.formatted(message);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/delete")
    public String deletePassword(
            @AuthenticationPrincipal AuthUserDetailsDto user,
            String serviceDomain
    ) throws NameNotFoundException {
        if(!serviceEntityService.existsByDomain(serviceDomain)){
            return "redirect:/error";
        }

        Long serviceId = serviceEntityService.getIdByDomain(serviceDomain);
        passwordEntityService.deletePassword(serviceId, user);

        String message = "Password was deleted";

        return REDIRECT_URL.formatted(message);
    }

    @GetMapping("/analysis")
    public String analyzeSystem(@AuthenticationPrincipal AuthUserDetailsDto user, Model model) throws NameNotFoundException, EncryptionException {
        List<AnalysisDto> analysisDtoList = passwordEntityService.analyzeSystem(user);

        model.addAttribute("analysisList", analysisDtoList);

        return "user/analysis";
    }
}
