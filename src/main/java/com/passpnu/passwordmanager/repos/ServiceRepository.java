package com.passpnu.passwordmanager.repos;

import com.passpnu.passwordmanager.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    boolean existsByDomain(@Param("domain") String domain);

    ServiceEntity findByDomain(String domain);
}
