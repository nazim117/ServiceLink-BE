package org.example.servicelinkbe.business.provision_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.business.provision_service.utilities.ProvisionConverter;
import org.example.servicelinkbe.domain.Provision;
import org.example.servicelinkbe.domain.get.GetAllProvisionsResponse;
import org.example.servicelinkbe.persistance.entity.AddressEntity;
import org.example.servicelinkbe.persistance.entity.ProvisionEntity;
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
class GetProvisionsUseCaseImplTest {
    @Mock
    private ProvisionRepo provisionRepo;
    @InjectMocks
    private GetProvisionsUseCaseImpl getProvisionsUseCase;
    @BeforeEach
    void setUp(){MockitoAnnotations.openMocks(this);}

    @Test
    void testGet() {
        AddressEntity addressEntity = AddressEntity.builder().street("street").city("city").country("country").postalCode("postalcode").build();
        ProvisionEntity entity1 = ProvisionEntity.builder().name("name").description("desc").address(addressEntity).build();
        ProvisionEntity entity2 = ProvisionEntity.builder().name("name").description("desc").address(addressEntity).build();
        List<ProvisionEntity> entities = Arrays.asList(entity1, entity2);

        when(provisionRepo.findAll()).thenReturn(entities);

        GetAllProvisionsResponse response = getProvisionsUseCase.get();

        assertEquals(2, response.getProvisions().size());
        verify(provisionRepo, times(1)).findAll();
    }
}