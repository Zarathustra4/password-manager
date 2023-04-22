package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.ServiceDto;
import com.passpnu.passwordmanager.entity.ServiceEntity;
import com.passpnu.passwordmanager.mapper.ServiceMapper;
import com.passpnu.passwordmanager.repos.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService{
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public List<ServiceDto> getServiceList() {
        List<ServiceEntity> serviceEntities = serviceRepository.findAll();
        List<ServiceDto> serviceDtoList = new ArrayList<>();
        for(ServiceEntity serviceEntity : serviceEntities){
            serviceDtoList.add(serviceMapper.entityToDto(serviceEntity));
        }
        return serviceDtoList;
    }

    @Override
    public ServiceDto postService(ServiceDto service){
        ServiceEntity serviceEntity = serviceMapper.dtoToEntity(service);
        return serviceMapper.entityToDto(serviceRepository.save(serviceEntity));
    }

    @Override
    public ServiceDto putService(ServiceDto service) throws NameNotFoundException {
        String domain = service.getDomain();
        ServiceEntity serviceEntity = Objects.requireNonNull(
                serviceRepository.findByDomain(domain),
                "The service is not found by the domain - " + domain
        );

        serviceEntity.setDescription(service.getDescription());
        serviceEntity.setLogoPath(service.getLogoPath());
        serviceEntity.setTitle(service.getTitle());
        serviceEntity.setDomain(service.getDomain());

        serviceRepository.save(serviceEntity);

        return serviceMapper.entityToDto(serviceEntity);
    }

    @Override
    public ServiceDto getById(String id) throws NameNotFoundException{
        Long longId = Long.parseLong(id);
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(longId);
        if(serviceEntity.isEmpty()){
            throw new NameNotFoundException("The service does not exist");
        }
        return serviceMapper.entityToDto(serviceEntity.get());
    }

    @Override
    public boolean existsByDomain(ServiceDto service){
        return serviceRepository.existsByDomain( service.getDomain() );
    }
}