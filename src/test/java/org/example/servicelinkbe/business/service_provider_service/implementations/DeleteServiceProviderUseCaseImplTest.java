package org.example.servicelinkbe.business.service_provider_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import org.example.servicelinkbe.TestConfig;
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
class DeleteServiceProviderUseCaseImplTest {
    @Mock
    private ProvisionRepo provisionRepo;
    @InjectMocks
    private DeleteServiceProviderUseCaseImpl deleteProvisionUseCase;
    @BeforeEach
    void setUp(){MockitoAnnotations.openMocks(this);}

    @Test
    void testDeleteSuccess() {
        ServiceProviderEntity serviceProviderEntity = new ServiceProviderEntity();
        when(provisionRepo.findById(1L)).thenReturn(java.util.Optional.of(serviceProviderEntity));

        deleteProvisionUseCase.delete(1L);

        verify(provisionRepo, times(1)).delete(serviceProviderEntity);
    }

    @Test
    void testDeleteNotFound() {
        when(provisionRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        try {
            deleteProvisionUseCase.delete(1L);
        } catch (EntityNotFoundException e) {
            assertEquals("Service not found", e.getMessage());
        }

        verify(provisionRepo, never()).delete(any(ServiceProviderEntity.class));
    }
}