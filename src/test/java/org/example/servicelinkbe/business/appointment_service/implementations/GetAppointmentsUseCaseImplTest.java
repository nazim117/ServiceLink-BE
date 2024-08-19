package org.example.servicelinkbe.business.appointment_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.business.appointment_service.utilities.AppointmentConverter;
import org.example.servicelinkbe.domain.Appointment;
import org.example.servicelinkbe.domain.get.GetAllAppointmentsResponse;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.example.servicelinkbe.persistance.entity.OfferEntity;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TestConfig.class})
class GetAppointmentsUseCaseImplTest {
    @Mock
    private AppointmentRepo appointmentRepo;
    @Mock
    private AppointmentConverter appointmentConverter;
    @InjectMocks
    private GetAppointmentsUseCaseImpl getAppointmentsUseCase;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGet() {
        // Arrange
        Long serviceId = 1L;
        AppointmentEntity entity1 = AppointmentEntity.builder().description("test desc1").offer(OfferEntity.builder().name("test name1").build()).build();
        AppointmentEntity entity2 = AppointmentEntity.builder().description("test desc2").offer(OfferEntity.builder().name("test name2").build()).build();

        List<AppointmentEntity> entities = List.of(entity1, entity2);

        Appointment appointment1 = Appointment.builder().description("test desc1").offer(OfferEntity.builder().name("test name1").build()).build();
        Appointment appointment2 = Appointment.builder().description("test desc2").offer(OfferEntity.builder().name("test name2").build()).build();

        when(appointmentRepo.findAllByServiceProvider_Id(serviceId)).thenReturn(entities);

        // Act
        GetAllAppointmentsResponse response = getAppointmentsUseCase.get(serviceId);

        // Assert
        assertEquals(2, response.getAppointments().size());
        assertEquals(List.of(appointment1, appointment2), response.getAppointments());
        verify(appointmentRepo).findAllByServiceProvider_Id(serviceId);
    }

}