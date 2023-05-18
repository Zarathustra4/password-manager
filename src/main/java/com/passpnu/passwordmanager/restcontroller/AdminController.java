package com.passpnu.passwordmanager.restcontroller;

import com.passpnu.passwordmanager.dto.user.ChangeRoleDto;
import com.passpnu.passwordmanager.dto.user.UserDto;
import com.passpnu.passwordmanager.service.UserEntityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private UserEntityService userEntityService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> saveAdmin(@RequestBody UserDto userDto){
        if(userEntityService.existsByUsername(userDto.getUsername())){
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        userEntityService.saveAdmin(userDto);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PutMapping("/change-role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> changeRole(@RequestBody ChangeRoleDto changeRoleDto) throws UsernameNotFoundException {
        userEntityService.changeRole(changeRoleDto);

        return new ResponseEntity<>("The role was changed", HttpStatus.OK);
    }
}