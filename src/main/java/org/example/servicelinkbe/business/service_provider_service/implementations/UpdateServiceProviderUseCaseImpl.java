package org.example.servicelinkbe.business.service_provider_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.service_provider_service.interfaces.UpdateServiceProviderUseCase;
import org.example.servicelinkbe.domain.create.CreateAddressRequest;
import org.example.servicelinkbe.domain.update.UpdateProvisionRequest;
import org.example.servicelinkbe.persistance.entity.AddressEntity;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateServiceProviderUseCaseImpl implements UpdateServiceProviderUseCase {
    private final ProvisionRepo provisionRepo;
    @Transactional
    @Override
    public void update(UpdateProvisionRequest request) {
        ServiceProviderEntity serviceProviderEntity = provisionRepo.findById(request.getId()).orElse(null);
        if (serviceProviderEntity == null) {
            throw new EntityNotFoundException("Service not found");
        }

        updateFields(request, serviceProviderEntity);
    }

    private void updateFields(UpdateProvisionRequest request, ServiceProviderEntity serviceProviderEntity) {
        CreateAddressRequest addressRequest = request.getAddress();
        AddressEntity addressEntity = AddressEntity.builder()
                .street(addressRequest.getStreet())
                .city(addressRequest.getCity())
                .country(addressRequest.getCountry())
                .postalCode(addressRequest.getPostalCode())
                .build();

        serviceProviderEntity.setId(request.getId());
        serviceProviderEntity.setName(request.getName());
        serviceProviderEntity.setDescription(request.getDescription());
        serviceProviderEntity.setAddress(addressEntity);

        provisionRepo.save(serviceProviderEntity);
    }
}
