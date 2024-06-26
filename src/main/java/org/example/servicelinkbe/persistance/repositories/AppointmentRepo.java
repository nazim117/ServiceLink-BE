package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity, Integer> {
}
