package org.example.servicelinkbe.business.provision_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.persistance.entity.ProvisionEntity;
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
class DeleteProvisionUseCaseImplTest {
    @Mock
    private ProvisionRepo provisionRepo;
    @InjectMocks
    private DeleteProvisionUseCaseImpl deleteProvisionUseCase;
    @BeforeEach
    void setUp(){MockitoAnnotations.openMocks(this);}

    @Test
    void testDeleteSuccess() {
        ProvisionEntity provisionEntity = new ProvisionEntity();
        when(provisionRepo.findById(1L)).thenReturn(java.util.Optional.of(provisionEntity));

        deleteProvisionUseCase.delete(1L);

        verify(provisionRepo, times(1)).delete(provisionEntity);
    }

    @Test
    void testDeleteNotFound() {
        when(provisionRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        try {
            deleteProvisionUseCase.delete(1L);
        } catch (EntityNotFoundException e) {
            assertEquals("Service not found", e.getMessage());
        }

        verify(provisionRepo, never()).delete(any(ProvisionEntity.class));
    }
}