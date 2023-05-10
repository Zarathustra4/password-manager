package com.passpnu.passwordmanager.mapper;

import com.passpnu.passwordmanager.dto.PasswordDto;
import com.passpnu.passwordmanager.entity.PasswordEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CustomPasswordMapper {
    private final PasswordMapper passwordMapper;

    public List<PasswordDto> entityListToDtoList(List<PasswordEntity> entities){
        List<PasswordDto> passwordDtoList = new ArrayList<>();
        for(PasswordEntity entity: entities){
            passwordDtoList.add(
                    passwordMapper.entityToDto(entity)
            );
        }
        return passwordDtoList;
    }
}
