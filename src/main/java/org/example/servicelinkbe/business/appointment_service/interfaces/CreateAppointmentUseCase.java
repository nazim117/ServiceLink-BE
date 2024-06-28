package org.example.servicelinkbe.business.appointment_service.interfaces;

import org.example.servicelinkbe.domain.create.CreateAppointmentRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;

public interface CreateAppointmentUseCase {
    CreateResponse create(CreateAppointmentRequest request);
}
