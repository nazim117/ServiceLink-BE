package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<AddressEntity, Integer> {
}
