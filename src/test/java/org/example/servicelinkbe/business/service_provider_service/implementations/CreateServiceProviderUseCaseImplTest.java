package org.example.servicelinkbe.business.service_provider_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.domain.create.CreateAddressRequest;
import org.example.servicelinkbe.domain.create.CreateServiceProviderRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.persistance.repositories.AddressRepo;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.example.servicelinkbe.persistance.repositories.UserRepo;
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
class CreateServiceProviderUseCaseImplTest {
    @Mock
    private ProvisionRepo provisionRepo;
    @Mock
    private AddressRepo addressRepo;
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private CreateServiceProviderUseCaseImpl createProvisionUseCase;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreate() {
        CreateAddressRequest addressRequest = CreateAddressRequest.builder().street("Street").city("City").country("Country").postalCode("PostalCode").build();
        CreateServiceProviderRequest request = CreateServiceProviderRequest.builder().name("Name").address(addressRequest).description("Description").build();

        ServiceProviderEntity savedEntity = ServiceProviderEntity.builder().id(1L).name("Name").description("Description").build();
        when(provisionRepo.save(any(ServiceProviderEntity.class))).thenReturn(savedEntity);

        CreateResponse response = createProvisionUseCase.create(request);

        assertEquals(1L, response.getId());
        verify(provisionRepo, times(1)).save(any(ServiceProviderEntity.class));
    }
}