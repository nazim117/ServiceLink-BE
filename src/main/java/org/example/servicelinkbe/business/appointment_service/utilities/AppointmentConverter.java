package org.example.servicelinkbe.business.appointment_service.utilities;

import lombok.NoArgsConstructor;
import org.example.servicelinkbe.domain.Appointment;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;

@NoArgsConstructor
public class AppointmentConverter {
    public static Appointment convert(AppointmentEntity appointmentEntity) {
        return Appointment.builder()
                .id(appointmentEntity.getId())
                .startDate(appointmentEntity.getStartDate())
                .endDate(appointmentEntity.getEndDate())
                .description(appointmentEntity.getDescription())
                .clientEmail(appointmentEntity.getClientEmail())
                .clientName(appointmentEntity.getClientName())
                .offer(appointmentEntity.getOffer())
                .build();
    }
}
