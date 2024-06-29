package org.example.servicelinkbe.business.service_provider_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.domain.create.CreateAddressRequest;
import org.example.servicelinkbe.domain.update.UpdateProvisionRequest;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TestConfig.class})
class UpdateServiceProviderUseCaseImplTest {
    @Mock
    private ProvisionRepo provisionRepo;
    @InjectMocks
    private UpdateServiceProviderUseCaseImpl updateProvisionUseCase;
    @BeforeEach
    void setUp(){MockitoAnnotations.openMocks(this);}

    @Test
    void testUpdateSuccess() {
        ServiceProviderEntity serviceProviderEntity = new ServiceProviderEntity();
        when(provisionRepo.findById(1L)).thenReturn(java.util.Optional.of(serviceProviderEntity));

        UpdateProvisionRequest request = UpdateProvisionRequest.builder()
                .id(1L)
                .name("Name")
                .address(CreateAddressRequest.builder()
                        .street("Street")
                        .city("City")
                        .country("Country")
                        .postalCode("PostalCode")
                        .build())
                .description("Description")
                .build();

        updateProvisionUseCase.update(request);

        verify(provisionRepo, times(1)).save(serviceProviderEntity);
        assertEquals("Name", serviceProviderEntity.getName());
        assertEquals("Description", serviceProviderEntity.getDescription());
    }

    @Test
    void testUpdateNotFound() {
        when(provisionRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        UpdateProvisionRequest request = UpdateProvisionRequest.builder()
                .id(1L)
                .name("Name")
                .address(CreateAddressRequest.builder()
                        .street("Street")
                        .city("City")
                        .country("Country")
                        .postalCode("PostalCode")
                        .build())
                .description("Description")
                .build();

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            updateProvisionUseCase.update(request);
        });

        assertEquals("Service not found", exception.getMessage());
        verify(provisionRepo, never()).save(any(ServiceProviderEntity.class));
    }
}