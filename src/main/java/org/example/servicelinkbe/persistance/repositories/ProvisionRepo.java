package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.ProvisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvisionRepo extends JpaRepository<ProvisionEntity, Integer> {
}
