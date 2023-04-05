package com.passpnu.passwordmanager.repos;

import com.passpnu.passwordmanager.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
}
