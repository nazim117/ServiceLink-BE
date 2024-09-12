package org.example.servicelinkbe.business.service_provider_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.business.service_provider_service.exceptions.ServiceProviderNotFoundException;
import org.example.servicelinkbe.domain.ServiceProvider;
import org.example.servicelinkbe.persistance.entity.AddressEntity;
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
class GetSingleServiceProviderUseCaseImplTest {
    @Mock
    private ProvisionRepo provisionRepo;
    @InjectMocks
    private GetSingleServiceProviderUseCaseImpl getSingleProvisionUseCase;
    @BeforeEach
    void setUp(){MockitoAnnotations.openMocks(this);}

    @Test
    void testGetSuccess() {
        AddressEntity addressEntity = AddressEntity.builder()
                .postalCode("postalCode")
                .country("country")
                .city("city")
                .street("street")
                .build();

        ServiceProviderEntity entity = ServiceProviderEntity.builder()
                .id(1L)
                .address(addressEntity)
                .build();

        when(provisionRepo.findById(1L)).thenReturn(java.util.Optional.of(entity));

        ServiceProvider serviceProvider = getSingleProvisionUseCase.get(1L);

        assertNotNull(serviceProvider);
        verify(provisionRepo, times(1)).findById(1L);
    }

    @Test
    void testGetNotFound() {
        when(provisionRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        ServiceProviderNotFoundException exception = assertThrows(ServiceProviderNotFoundException.class, () -> {
            getSingleProvisionUseCase.get(1L);
        });

        assertEquals("Service provider with ID 1 not found", exception.getMessage());
        verify(provisionRepo, times(1)).findById(1L);
    }
}