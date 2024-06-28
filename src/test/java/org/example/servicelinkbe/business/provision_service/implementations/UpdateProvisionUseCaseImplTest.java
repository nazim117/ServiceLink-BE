package org.example.servicelinkbe.business.provision_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.domain.create.CreateAddressRequest;
import org.example.servicelinkbe.domain.update.UpdateProvisionRequest;
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
class UpdateProvisionUseCaseImplTest {
    @Mock
    private ProvisionRepo provisionRepo;
    @InjectMocks
    private UpdateProvisionUseCaseImpl updateProvisionUseCase;
    @BeforeEach
    void setUp(){MockitoAnnotations.openMocks(this);}

    @Test
    void testUpdateSuccess() {
        ProvisionEntity provisionEntity = new ProvisionEntity();
        when(provisionRepo.findById(1L)).thenReturn(java.util.Optional.of(provisionEntity));

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

        verify(provisionRepo, times(1)).save(provisionEntity);
        assertEquals("Name", provisionEntity.getName());
        assertEquals("Description", provisionEntity.getDescription());
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
        verify(provisionRepo, never()).save(any(ProvisionEntity.class));
    }
}