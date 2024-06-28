package org.example.servicelinkbe.business.provision_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.domain.create.CreateAddressRequest;
import org.example.servicelinkbe.domain.create.CreateProvisionRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.persistance.entity.ProvisionEntity;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TestConfig.class})
class CreateProvisionUseCaseImplTest {
    @Mock
    private ProvisionRepo provisionRepo;
    @InjectMocks
    private CreateProvisionUseCaseImpl createProvisionUseCase;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreate() {
        CreateAddressRequest addressRequest = CreateAddressRequest.builder().street("Street").city("City").country("Country").postalCode("PostalCode").build();
        CreateProvisionRequest request = CreateProvisionRequest.builder().name("Name").address(addressRequest).description("Description").build();

        ProvisionEntity savedEntity = ProvisionEntity.builder().id(1L).name("Name").description("Description").build();
        when(provisionRepo.save(any(ProvisionEntity.class))).thenReturn(savedEntity);

        CreateResponse response = createProvisionUseCase.create(request);

        assertEquals(1L, response.getId());
        verify(provisionRepo, times(1)).save(any(ProvisionEntity.class));
    }
}