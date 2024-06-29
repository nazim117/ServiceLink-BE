package org.example.servicelinkbe.business.appointment_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.appointment_service.interfaces.UpdateAppointmentUseCase;
import org.example.servicelinkbe.business.appointment_service.utilities.AppointmentConverter;
import org.example.servicelinkbe.domain.update.UpdateAppointmentRequest;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateAppointmentUseCaseImpl implements UpdateAppointmentUseCase {
    private final AppointmentRepo appointmentRepo;
    @Transactional
    @Override
    public void update(UpdateAppointmentRequest request) {
        AppointmentEntity appointmentEntity = appointmentRepo.findById(request.getId()).orElse(null);
        if (appointmentEntity == null) {
            throw new EntityNotFoundException("Appointment not found");
        }

        updateFields(request, appointmentEntity);
    }

    private void updateFields(UpdateAppointmentRequest request, AppointmentEntity appointmentEntity) {
        appointmentEntity.setId(request.getId());
        appointmentEntity.setUpdatedAt(request.getDatetime());
        appointmentEntity.setDescription(request.getDescription());

        appointmentRepo.save(appointmentEntity);
    }
}
