package org.example.servicelinkbe.business.appointment_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.domain.create.CreateAppointmentRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
class CreateAppointmentUseCaseImplTest {
    @Mock
    private AppointmentRepo appointmentRepo;
    @InjectMocks
    private CreateAppointmentUseCaseImpl createAppointmentUseCase;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testCreate() {
        // Arrange
        CreateAppointmentRequest request = new CreateAppointmentRequest(LocalDateTime.now(), "Description");
        AppointmentEntity mockedEntity = new AppointmentEntity();
        mockedEntity.setId(1L);

        when(appointmentRepo.save(any(AppointmentEntity.class))).thenReturn(mockedEntity);

        // Act
        CreateResponse response = createAppointmentUseCase.create(request);

        // Assert
        assertEquals(1L, response.getId());
        verify(appointmentRepo).save(any(AppointmentEntity.class));
    }
}