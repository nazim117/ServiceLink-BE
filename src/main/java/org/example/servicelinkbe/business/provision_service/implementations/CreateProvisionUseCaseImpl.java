package org.example.servicelinkbe.business.provision_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.provision_service.interfaces.CreateProvisionUseCase;
import org.example.servicelinkbe.business.provision_service.utilities.AddressConverter;
import org.example.servicelinkbe.domain.create.CreateAddressRequest;
import org.example.servicelinkbe.domain.create.CreateProvisionRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.persistance.entity.AddressEntity;
import org.example.servicelinkbe.persistance.entity.ProvisionEntity;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProvisionUseCaseImpl implements CreateProvisionUseCase {
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

        ProvisionEntity provisionEntity = ProvisionEntity
                .builder()
                .name(request.getName())
                .address(addressEntity)
                .description(request.getDescription())
                .build();

        Long provisionId= provisionRepo.save(provisionEntity).getId();
        return CreateResponse.builder().id(provisionId).build();
    }
}
