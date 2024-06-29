package org.example.servicelinkbe.business.service_provider_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.service_provider_service.interfaces.CreateServiceProviderUseCase;
import org.example.servicelinkbe.domain.create.CreateAddressRequest;
import org.example.servicelinkbe.domain.create.CreateProvisionRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.persistance.entity.AddressEntity;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateServiceProviderUseCaseImpl implements CreateServiceProviderUseCase {
    private final ProvisionRepo provisionRepo;

    @Transactional
    @Override
    public CreateResponse create(CreateProvisionRequest request) {
        CreateAddressRequest addressRequest = request.getAddress();
        AddressEntity addressEntity = AddressEntity.builder()
                .street(addressRequest.getStreet())
                .city(addressRequest.getCity())
                .country(addressRequest.getCountry())
                .postalCode(addressRequest.getPostalCode())
                .build();

        ServiceProviderEntity serviceProviderEntity = ServiceProviderEntity
                .builder()
                .name(request.getName())
                .address(addressEntity)
                .description(request.getDescription())
                .build();

        Long provisionId= provisionRepo.save(serviceProviderEntity).getId();
        return CreateResponse.builder().id(provisionId).build();
    }
}
