package org.example.servicelinkbe.business.appointment_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.appointment_service.interfaces.DeleteAppointmentUseCase;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.example.servicelinkbe.persistance.entity.ProvisionEntity;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteAppointmentUseCaseImpl implements DeleteAppointmentUseCase {
    private final AppointmentRepo appointmentRepo;
    @Transactional
    @Override
    public void delete(Long id) {
        AppointmentEntity appointmentEntity = appointmentRepo.findById(id).orElse(null);
        if(appointmentEntity != null) {
            appointmentRepo.delete(appointmentEntity);
        }else {
            throw new EntityNotFoundException("Service not found");
        }
    }
}
