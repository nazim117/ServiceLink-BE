package org.example.servicelinkbe.business.appointment_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
class DeleteAppointmentUseCaseImplTest {
    @Mock
    private AppointmentRepo appointmentRepo;
    @InjectMocks
    private DeleteAppointmentUseCaseImpl deleteAppointmentUseCase;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteExisting() {
        // Arrange
        Long appointmentId = 1L;
        AppointmentEntity foundEntity = new AppointmentEntity();
        when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.of(foundEntity));

        // Act
        deleteAppointmentUseCase.delete(appointmentId);

        // Assert
        verify(appointmentRepo).delete(foundEntity);
    }

    @Test
    public void testDeleteNonExisting() {
        // Arrange
        Long appointmentId = 1L;
        when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> deleteAppointmentUseCase.delete(appointmentId));
    }
}