package org.example.servicelinkbe.business.appointment_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.business.appointment_service.utilities.AppointmentConverter;
import org.example.servicelinkbe.domain.Appointment;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
class GetSingleAppointmentUseCaseImplTest {
    @Mock
    private AppointmentRepo appointmentRepo;
    @Mock
    private AppointmentConverter appointmentConverter;
    @InjectMocks
    private GetSingleAppointmentUseCaseImpl getSingleAppointmentUseCase;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetExistingAppointment() {
        long appointmentId = 1l;
        AppointmentEntity mockAppointment = createMockAppointment(appointmentId, LocalDateTime.now());
        when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.ofNullable(mockAppointment));

        Appointment appointment = getSingleAppointmentUseCase.get(appointmentId);

        assertNotNull(appointment);
        assertEquals(appointmentId, appointment.getId());
    }

    private AppointmentEntity createMockAppointment(long appointmentId, LocalDateTime date) {
        return AppointmentEntity.builder()
                .id(appointmentId)
                .createdAt(date)
                .description("Check-up")
                .build();
    }

    @Test
    public void testGetNonExistingAppointment() {
        // Arrange
        Long appointmentId = 999L;
        when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> getSingleAppointmentUseCase.get(appointmentId));
    }
}