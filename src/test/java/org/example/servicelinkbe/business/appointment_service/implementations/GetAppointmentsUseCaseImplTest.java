package org.example.servicelinkbe.business.appointment_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.domain.get.GetAllAppointmentsResponse;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
class GetAppointmentsUseCaseImplTest {
    @Mock
    private AppointmentRepo appointmentRepo;
    @InjectMocks
    private GetAppointmentsUseCaseImpl getAppointmentsUseCase;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGet() {
        // Arrange
        List<AppointmentEntity> entities = List.of(new AppointmentEntity(), new AppointmentEntity());
        when(appointmentRepo.findAll()).thenReturn(entities);

        // Act
        GetAllAppointmentsResponse response = getAppointmentsUseCase.get();

        // Assert
        assertEquals(2, response.getAppointments().size());
        verify(appointmentRepo).findAll();
    }
}