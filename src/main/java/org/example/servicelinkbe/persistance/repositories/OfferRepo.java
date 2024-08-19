package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepo extends JpaRepository<OfferEntity, Integer> {
    boolean existsByName(String name);
    List<OfferEntity> findAllById(Long id);
    List<OfferEntity> findAllByServiceProvider_Id(Long id);
    Optional<OfferEntity> findById(Long id);
}
