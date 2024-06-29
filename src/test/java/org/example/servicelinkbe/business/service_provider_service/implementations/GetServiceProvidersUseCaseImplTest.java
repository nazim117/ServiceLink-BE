package org.example.servicelinkbe.business.service_provider_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.domain.get.GetAllProvisionsResponse;
import org.example.servicelinkbe.persistance.entity.AddressEntity;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TestConfig.class})
class GetServiceProvidersUseCaseImplTest {
    @Mock
    private ProvisionRepo provisionRepo;
    @InjectMocks
    private GetServiceProvidersUseCaseImpl getProvisionsUseCase;
    @BeforeEach
    void setUp(){MockitoAnnotations.openMocks(this);}

    @Test
    void testGet() {
        AddressEntity addressEntity = AddressEntity.builder().street("street").city("city").country("country").postalCode("postalcode").build();
        ServiceProviderEntity entity1 = ServiceProviderEntity.builder().name("name").description("desc").address(addressEntity).build();
        ServiceProviderEntity entity2 = ServiceProviderEntity.builder().name("name").description("desc").address(addressEntity).build();
        List<ServiceProviderEntity> entities = Arrays.asList(entity1, entity2);

        when(provisionRepo.findAll()).thenReturn(entities);

        GetAllProvisionsResponse response = getProvisionsUseCase.get();

        assertEquals(2, response.getServiceProviders().size());
        verify(provisionRepo, times(1)).findAll();
    }
}