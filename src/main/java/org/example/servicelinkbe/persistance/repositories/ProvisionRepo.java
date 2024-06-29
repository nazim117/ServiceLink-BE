package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvisionRepo extends JpaRepository<ServiceProviderEntity, Integer> {
    Optional<ServiceProviderEntity> findById(Long id);
}
