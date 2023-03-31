package com.passpnu.passwordmanager.repos;

import com.passpnu.passwordmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
