package com.passpnu.passwordmanager.mapper;

import com.passpnu.passwordmanager.dto.ServiceDto;
import com.passpnu.passwordmanager.entity.ServiceEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class ServiceMapper {
    public ServiceEntity dtoToEntity(ServiceDto serviceDto){
        return ServiceEntity.builder()
                .title(serviceDto.getTitle())
                .logoPath(serviceDto.getLogoPath())
                .domain(serviceDto.getDomain())
                .description(serviceDto.getDescription())
                .build();
    }

    public ServiceDto entityToDto(ServiceEntity serviceEntity){
        return ServiceDto.builder()
                .title(serviceEntity.getTitle())
                .logoPath(serviceEntity.getLogoPath())
                .domain(serviceEntity.getDomain())
                .description(serviceEntity.getDescription())
                .build();
    }
}
