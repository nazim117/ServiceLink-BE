package org.example.servicelinkbe.business.appointment_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.domain.create.CreateAppointmentRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.persistance.entity.AddressEntity;
import org.example.servicelinkbe.persistance.entity.AppointmentEntity;
import org.example.servicelinkbe.persistance.entity.OfferEntity;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.persistance.repositories.AppointmentRepo;
import org.example.servicelinkbe.persistance.repositories.OfferRepo;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
class CreateAppointmentUseCaseImplTest {
    @Mock
    private AppointmentRepo appointmentRepo;
    @Mock
    private ProvisionRepo provisionRepo;
    @Mock
    private OfferRepo offerRepo;
    @InjectMocks
    private CreateAppointmentUseCaseImpl createAppointmentUseCase;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        // Arrange
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .clientEmail("testemail@example.com")
                .clientName("ClientName")
                .description("Description")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .offerId(1L)
                .serviceId(1L)
                .build();
        AppointmentEntity mockedEntity = new AppointmentEntity();
        mockedEntity.setId(1L);

        ServiceProviderEntity serviceProvider = ServiceProviderEntity.builder()
                .id(1L)
                .name("Name")
                .description("Description")
                .imagePath("path")
                .address(AddressEntity.builder().street("street1").build())
                .build();

        OfferEntity offerEntity = OfferEntity.builder().duration(Duration.ofSeconds(150)).build();

        when(provisionRepo.findById(anyLong())).thenReturn(Optional.of(serviceProvider));
        when(offerRepo.findById(anyLong())).thenReturn(Optional.of(offerEntity));
        when(appointmentRepo.save(any(AppointmentEntity.class))).thenReturn(mockedEntity);

        // Act
        CreateResponse response = createAppointmentUseCase.create(request);

        // Assert
        assertEquals(1L, response.getId());
        verify(appointmentRepo).save(any(AppointmentEntity.class));
    }
}
