package com.passpnu.passwordmanager.mapper;
import com.passpnu.passwordmanager.dto.password.PasswordDto;
import com.passpnu.passwordmanager.dto.password.PasswordServiceIdDto;
import com.passpnu.passwordmanager.entity.PasswordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PasswordMapper {
    PasswordDto entityToDto(PasswordEntity entity);

    PasswordServiceIdDto entityToPasswordServiceIdDto(PasswordEntity entity);
}
