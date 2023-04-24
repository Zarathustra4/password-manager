package com.passpnu.passwordmanager.mapper;

import com.passpnu.passwordmanager.dto.ServiceDto;
import com.passpnu.passwordmanager.entity.ServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceEntity dtoToEntity(ServiceDto serviceDto);

    ServiceDto entityToDto(ServiceEntity serviceEntity);
}