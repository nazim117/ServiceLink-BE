package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity, Integer> {
    Optional<AppointmentEntity> findById(Long appointmentId);
}
