package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.ServiceDto;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface ServiceEntityService {
    List<ServiceDto> getServiceList();

    ServiceDto postService(ServiceDto service);

    ServiceDto putService(ServiceDto service) throws NameNotFoundException;

    boolean existsByDomain(ServiceDto serviceDto);

    ServiceDto getById(String id) throws NameNotFoundException;
}
