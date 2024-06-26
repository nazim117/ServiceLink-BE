package org.example.servicelinkbe.controller;

import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.appointment_service.implementations.GetSingleAppointmentUseCaseImpl;
import org.example.servicelinkbe.business.appointment_service.interfaces.CreateAppointmentUseCase;
import org.example.servicelinkbe.business.appointment_service.interfaces.GetAppointmentsUseCase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
@AllArgsConstructor
public class AppointmentController {
    private final GetAppointmentsUseCase getAppointmentsUseCase;
    private final GetSingleAppointmentUseCaseImpl getSingleAppointmentUseCase;
    private final CreateAppointmentUseCase createAppointmentUseCase;
}
