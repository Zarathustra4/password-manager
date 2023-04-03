package com.passpnu.passwordmanager.repos;

import com.passpnu.passwordmanager.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
