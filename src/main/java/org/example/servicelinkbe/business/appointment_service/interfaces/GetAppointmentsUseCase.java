package org.example.servicelinkbe.business.appointment_service.interfaces;

import org.example.servicelinkbe.domain.get.GetAllAppointmentsResponse;

public interface GetAppointmentsUseCase {
    GetAllAppointmentsResponse get(Long serviceId);
}
