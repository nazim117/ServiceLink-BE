package org.example.servicelinkbe.business.appointment_service.interfaces;

import org.example.servicelinkbe.domain.Appointment;

public interface GetSingleAppointmentUseCase {
    Appointment get(Long id);
}
