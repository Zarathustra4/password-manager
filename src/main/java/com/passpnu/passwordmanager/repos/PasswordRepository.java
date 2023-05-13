package com.passpnu.passwordmanager.repos;

import com.passpnu.passwordmanager.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {
    PasswordEntity findByServiceId(Long serviceId);
    List<PasswordEntity> findAllByUserId(Long userId);

    PasswordEntity findByUserIdAndServiceId(Long userId, Long serviceId);

    Boolean existsByUserIdAndServiceId(Long userId, Long serviceId);

    void deleteByUserIdAndServiceId(Long userId, Long serviceId);
}
