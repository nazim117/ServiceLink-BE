package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepo extends JpaRepository<OfferEntity, Integer> {
}
