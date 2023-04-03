package com.passpnu.passwordmanager.repos;

import com.passpnu.passwordmanager.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {

}
