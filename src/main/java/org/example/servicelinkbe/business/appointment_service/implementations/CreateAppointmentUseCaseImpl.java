package org.example.servicelinkbe.business.appointment_service.implementations;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.appointment_service.interfaces.CreateAppointmentUseCase;
import org.example.servicelinkbe.domain.create.CreateAppointmentRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.example.servicelinkbe.persistance.entity.OfferEntity;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.example.servicelinkbe.persistance.repositories.OfferRepo;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreateAppointmentUseCaseImpl implements CreateAppointmentUseCase {
    private final AppointmentRepo appointmentRepo;
    private final ProvisionRepo serviceRepo;
    private final OfferRepo offerRepo;

    @Transactional
    @Override
    public CreateResponse create(CreateAppointmentRequest request) {
        LocalDateTime startDate = request.getStartDate();
        LocalDateTime endDate = request.getEndDate();

        if (appointmentRepo.existsByCreatedAt(startDate)) {
            throw new EntityExistsException("Appointment already exists for this date");
        }

        ServiceProviderEntity service = serviceRepo.findById(request.getServiceId())
                .orElseThrow(() -> new EntityExistsException("Service not found"));
        OfferEntity offer = offerRepo.findById(request.getOfferId())
                .orElseThrow(() -> new EntityExistsException("Offer not found"));

        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                .startDate(startDate)
                .endDate(endDate)
                .service(service)
                .offer(offer)
                .clientName(request.getClientName())
                .clientEmail(request.getClientEmail())
                .description(request.getDescription())
                .build();

        Long provisionId = appointmentRepo.save(appointmentEntity).getId();
        return CreateResponse.builder().id(provisionId).build();
    }
}
