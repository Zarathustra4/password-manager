package com.passpnu.passwordmanager.repos;

import com.passpnu.passwordmanager.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
