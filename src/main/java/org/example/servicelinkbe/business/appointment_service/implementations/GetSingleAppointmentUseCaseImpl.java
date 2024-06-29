package org.example.servicelinkbe.business.appointment_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.appointment_service.interfaces.GetSingleAppointmentUseCase;
import org.example.servicelinkbe.business.appointment_service.utilities.AppointmentConverter;
import org.example.servicelinkbe.domain.Appointment;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetSingleAppointmentUseCaseImpl implements GetSingleAppointmentUseCase {
    private final AppointmentRepo appointmentRepo;
    @Transactional
    @Override
    public Appointment get(Long id) {
        AppointmentEntity appointmentEntity = appointmentRepo.findById(id).orElse(null);
        if (appointmentEntity == null) {
            throw new EntityNotFoundException("Appointment not found");
        }

        return AppointmentConverter.convert(appointmentEntity);
    }
}
