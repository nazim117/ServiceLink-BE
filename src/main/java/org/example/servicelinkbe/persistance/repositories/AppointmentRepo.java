package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity, Integer> {
    Optional<AppointmentEntity> findById(Long appointmentId);
    List<AppointmentEntity> findAllByService_Id(Long serviceId);
    boolean existsByCreatedAt(LocalDateTime createdAt);
}
