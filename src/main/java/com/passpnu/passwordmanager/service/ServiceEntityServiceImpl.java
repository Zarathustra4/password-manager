package com.passpnu.passwordmanager.service;

import com.passpnu.passwordmanager.dto.ServiceDto;
import com.passpnu.passwordmanager.entity.ServiceEntity;
import com.passpnu.passwordmanager.repos.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService{
    ServiceRepository serviceRepository;

    private ServiceEntity dtoToEntity(ServiceDto serviceDto){
        return ServiceEntity.builder()
                .title(serviceDto.getTitle())
                .logoPath(serviceDto.getLogoPath())
                .domain(serviceDto.getDomain())
                .description(serviceDto.getDescription())
                .build();
    }

    private ServiceDto entityToDto(ServiceEntity serviceEntity){
        return ServiceDto.builder()
                .title(serviceEntity.getTitle())
                .logoPath(serviceEntity.getLogoPath())
                .domain(serviceEntity.getDomain())
                .description(serviceEntity.getDescription())
                .build();
    }

    @Override
    public List<ServiceDto> getServiceList() {
        List<ServiceEntity> serviceEntities = serviceRepository.findAll();
        List<ServiceDto> serviceDtoList = new ArrayList<>();
        for(ServiceEntity serviceEntity : serviceEntities){
            serviceDtoList.add(
                    entityToDto(serviceEntity)
            );
        }
        return serviceDtoList;
    }

    @Override
    public ServiceDto postService(ServiceDto service){
        ServiceEntity serviceEntity = dtoToEntity(service);

        return entityToDto(
                serviceRepository.save(serviceEntity)
        );
    }

    @Override
    public ServiceDto putService(ServiceDto service) throws NameNotFoundException {
        Optional<ServiceEntity> serviceOptional = serviceRepository.findByDomain(
                service.getDomain()
        );

        if(serviceOptional.isEmpty()){
            throw new NameNotFoundException("The service is not found");
        }

        ServiceEntity serviceEntity = serviceOptional.get();

        serviceEntity.setDescription(service.getDescription());
        serviceEntity.setLogoPath(service.getLogoPath());
        serviceEntity.setTitle(service.getTitle());
        serviceEntity.setDomain(service.getDomain());

        serviceRepository.save(serviceEntity);

        return entityToDto(serviceEntity);
    }

    @Override
    public ServiceDto getById(String id) throws NameNotFoundException{
        Long longId = Long.parseLong(id);
        Optional<ServiceEntity> serviceEntityOptional = serviceRepository.findById(longId);
        if(serviceEntityOptional.isEmpty()){
            throw new NameNotFoundException("The service does not exist");
        }

        return entityToDto(serviceEntityOptional.get());
    }

    @Override
    public boolean existsByDomain(ServiceDto service){
        return serviceRepository.existsByDomain( service.getDomain() );
    }
}
