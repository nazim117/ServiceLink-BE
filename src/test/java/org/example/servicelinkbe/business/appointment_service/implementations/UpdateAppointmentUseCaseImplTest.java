package org.example.servicelinkbe.business.appointment_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.domain.update.UpdateAppointmentRequest;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
class UpdateAppointmentUseCaseImplTest {
    @Mock
    private AppointmentRepo appointmentRepo;
    @InjectMocks
    private UpdateAppointmentUseCaseImpl updateAppointmentUseCase;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateExistingAppointment() {
        // Arrange
        UpdateAppointmentRequest request = new UpdateAppointmentRequest(1L, LocalDateTime.now().plusDays(1), "Updated Description");
        AppointmentEntity foundEntity = new AppointmentEntity();
        foundEntity.setId(request.getId());
        foundEntity.setUpdatedAt(LocalDateTime.now());
        foundEntity.setDescription("Initial Description");

        when(appointmentRepo.findById(request.getId())).thenReturn(Optional.of(foundEntity));
        when(appointmentRepo.save(foundEntity)).thenReturn(foundEntity);

        // Act
        updateAppointmentUseCase.update(request);

        // Assert
        verify(appointmentRepo).save(foundEntity);
        assertEquals(request.getDescription(), foundEntity.getDescription());
        assertEquals(request.getDatetime(), foundEntity.getUpdatedAt());
    }

    @Test
    public void testUpdateNonExistingAppointment() {
        // Arrange
        UpdateAppointmentRequest request = new UpdateAppointmentRequest(999L, LocalDateTime.now().plusDays(1), "Updated Description");
        when(appointmentRepo.findById(request.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> updateAppointmentUseCase.update(request));
    }
}