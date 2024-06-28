package org.example.servicelinkbe.business.appointment_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.appointment_service.interfaces.GetAppointmentsUseCase;
import org.example.servicelinkbe.business.appointment_service.utilities.AppointmentConverter;
import org.example.servicelinkbe.domain.Appointment;
import org.example.servicelinkbe.domain.get.GetAllAppointmentsResponse;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAppointmentsUseCaseImpl implements GetAppointmentsUseCase {
    private final AppointmentRepo appointmentRepo;
    @Transactional
    @Override
    public GetAllAppointmentsResponse get() {
        List<Appointment> appointments = appointmentRepo.findAll()
                .stream()
                .map(AppointmentConverter::convert)
                .toList();

        return GetAllAppointmentsResponse
                .builder()
                .appointments(appointments)
                .build();
    }
}
