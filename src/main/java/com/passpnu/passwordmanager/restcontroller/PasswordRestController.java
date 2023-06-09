package com.passpnu.passwordmanager.restcontroller;

import com.passpnu.passwordmanager.dto.AnalysisDto;
import com.passpnu.passwordmanager.dto.password.GuestPasswordDto;
import com.passpnu.passwordmanager.dto.user.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.password.PasswordResponseDto;
import com.passpnu.passwordmanager.dto.password.PasswordServiceIdDto;
import com.passpnu.passwordmanager.exception.EncryptionException;
import com.passpnu.passwordmanager.exception.PasswordServiceMappingException;
import com.passpnu.passwordmanager.exception.ServiceOccupiedException;
import com.passpnu.passwordmanager.service.PasswordEntityService;
import com.passpnu.passwordmanager.util.PasswordTestAnswer;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NameNotFoundException;
import java.util.List;



@RestController
@RequestMapping("/passwords")
@AllArgsConstructor
public class PasswordRestController {

    private final PasswordEntityService passwordEntityService;

    @GetMapping("/generate")
    public PasswordResponseDto generatePassword(){
        return passwordEntityService.generatePassword();
    }

    @PostMapping("/generate")
    @PreAuthorize("hasRole('ROLE_USER')")
    public PasswordResponseDto generateAndStorePassword(
            @AuthenticationPrincipal AuthUserDetailsDto userDetailsDto,
            @RequestBody PasswordServiceIdDto passwordServiceIdDto
    ) throws NameNotFoundException, EncryptionException, ServiceOccupiedException {
        return passwordEntityService.generateAndStorePassword(passwordServiceIdDto, userDetailsDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> storePassword(@AuthenticationPrincipal AuthUserDetailsDto userDetailsDto,
                                                        @RequestBody PasswordServiceIdDto passwordServiceIdDto)
            throws NameNotFoundException, EncryptionException, ServiceOccupiedException {

        passwordEntityService.savePassword(passwordServiceIdDto, userDetailsDto);
        return new ResponseEntity<>("Password was stored", HttpStatus.OK);
    }

    @GetMapping("/{serviceId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public PasswordResponseDto getPassword(@AuthenticationPrincipal AuthUserDetailsDto userDetailsDto, @PathVariable Long serviceId) throws EncryptionException {
        return passwordEntityService.getPassword(serviceId, userDetailsDto);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<PasswordServiceIdDto> getPasswordList(@AuthenticationPrincipal AuthUserDetailsDto userDetailsDto){
        return passwordEntityService.getPasswordList(userDetailsDto);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal AuthUserDetailsDto userDetailsDto,
            @RequestBody PasswordServiceIdDto passwordServiceIdDto) throws NameNotFoundException, EncryptionException, PasswordServiceMappingException {
        passwordEntityService.changePassword(passwordServiceIdDto, userDetailsDto);

        return new ResponseEntity<>("Password was changed", HttpStatus.OK);
    }

    @DeleteMapping("/{serviceId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> deletePassword(
            @AuthenticationPrincipal AuthUserDetailsDto userDetailsDto,
            @PathVariable Long serviceId
    ){
        passwordEntityService.deletePassword(serviceId, userDetailsDto);
        return new ResponseEntity<>("The password was deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/advice")
    public PasswordTestAnswer checkPasswordStrength(@RequestBody GuestPasswordDto passwordDto){
        String password = passwordDto.getPassword();
        return passwordEntityService.checkPasswordStrength(password);
    }

    @GetMapping("/analysis")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<AnalysisDto> analyzePasswordSystem(@AuthenticationPrincipal AuthUserDetailsDto authUserDetailsDto) throws EncryptionException, NameNotFoundException {
        return passwordEntityService.analyzeSystem(authUserDetailsDto);
    }
}