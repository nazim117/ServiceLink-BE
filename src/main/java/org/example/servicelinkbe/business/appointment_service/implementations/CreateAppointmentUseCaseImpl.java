package org.example.servicelinkbe.business.appointment_service.implementations;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.appointment_service.interfaces.CreateAppointmentUseCase;
import org.example.servicelinkbe.domain.Appointment;
import org.example.servicelinkbe.domain.create.CreateAppointmentRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateAppointmentUseCaseImpl implements CreateAppointmentUseCase {
    private final AppointmentRepo appointmentRepo;
    @Transactional
    @Override
    public CreateResponse create(CreateAppointmentRequest request) {
        if(appointmentRepo.existsByCreatedAt(request.getDatetime())){
            throw new EntityExistsException("Appointment already exists for this date");
        }
        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                .createdAt(request.getDatetime())
                .description(request.getDescription())
                .build();

        Long provisionId= appointmentRepo.save(appointmentEntity).getId();
        return CreateResponse.builder().id(provisionId).build();
    }
}
