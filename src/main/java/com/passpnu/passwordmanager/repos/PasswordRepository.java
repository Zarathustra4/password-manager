package com.passpnu.passwordmanager.repos;

import com.passpnu.passwordmanager.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {

}
