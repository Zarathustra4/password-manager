package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.service.ServiceDto;
import com.passpnu.passwordmanager.entity.ServiceEntity;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface ServiceEntityService {
    List<ServiceDto> getServiceList();

    ServiceDto postService(ServiceDto service);

    ServiceDto putService(ServiceDto service) throws NameNotFoundException;

    boolean existsByDomain(ServiceDto serviceDto);

    ServiceDto getById(String id) throws NameNotFoundException;

    ServiceEntity getEntityById(Long id) throws NameNotFoundException;

    String getDomainById(Long id) throws NameNotFoundException;
}
