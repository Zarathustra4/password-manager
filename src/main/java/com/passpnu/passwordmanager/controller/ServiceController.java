package com.passpnu.passwordmanager.controller;

import com.passpnu.passwordmanager.dto.ServiceDto;
import com.passpnu.passwordmanager.service.ServiceEntityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/services")
@AllArgsConstructor
public class ServiceController {
    private final ServiceEntityService serviceEntityService;

    @GetMapping
    public List<ServiceDto> getServiceList(){
        return serviceEntityService.getServiceList();
    }

    @PutMapping
    public ResponseEntity<String> editService(@RequestBody ServiceDto service) throws NameNotFoundException {
        //The existence check was redundant because it is already present in the service method

        ServiceDto serviceDto = serviceEntityService.putService(service);

        if(serviceDto != null){
            return new ResponseEntity<>("Service is successfully updated", HttpStatus.OK);
        }

        return new ResponseEntity<>("It is not possible to update the service", HttpStatus.BAD_REQUEST);
    }


    @PostMapping
    public ResponseEntity<ServiceDto> postService(@RequestBody ServiceDto service){
        return new ResponseEntity<>(
                serviceEntityService.postService(service),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ServiceDto getService(@PathVariable String id) throws NameNotFoundException {
        return serviceEntityService.getById(id);
    }
}
