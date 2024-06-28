package org.example.servicelinkbe.business.provision_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.business.provision_service.utilities.ProvisionConverter;
import org.example.servicelinkbe.domain.Provision;
import org.example.servicelinkbe.persistance.entity.AddressEntity;
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
class GetSingleProvisionUseCaseImplTest {
    @Mock
    private ProvisionRepo provisionRepo;
    @InjectMocks
    private GetSingleProvisionUseCaseImpl getSingleProvisionUseCase;
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

        ProvisionEntity entity = ProvisionEntity.builder()
                .id(1L)
                .address(addressEntity)
                .build();

        when(provisionRepo.findById(1L)).thenReturn(java.util.Optional.of(entity));

        Provision provision = getSingleProvisionUseCase.get(1L);

        assertNotNull(provision);
        verify(provisionRepo, times(1)).findById(1L);
    }

    @Test
    void testGetNotFound() {
        when(provisionRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            getSingleProvisionUseCase.get(1L);
        });

        assertEquals("Service not found", exception.getMessage());
        verify(provisionRepo, times(1)).findById(1L);
    }
}