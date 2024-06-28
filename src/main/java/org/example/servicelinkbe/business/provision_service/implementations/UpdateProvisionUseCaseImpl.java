package org.example.servicelinkbe.business.provision_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.provision_service.interfaces.UpdateProvisionUseCase;
import org.example.servicelinkbe.domain.create.CreateAddressRequest;
import org.example.servicelinkbe.domain.update.UpdateProvisionRequest;
import org.example.servicelinkbe.persistance.entity.AddressEntity;
import org.example.servicelinkbe.persistance.entity.ProvisionEntity;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateProvisionUseCaseImpl implements UpdateProvisionUseCase {
    private final ProvisionRepo provisionRepo;
    @Transactional
    @Override
    public void update(UpdateProvisionRequest request) {
        ProvisionEntity provisionEntity = provisionRepo.findById(request.getId()).orElse(null);
        if (provisionEntity == null) {
            throw new EntityNotFoundException("Service not found");
        }

        updateFields(request, provisionEntity);
    }

    private void updateFields(UpdateProvisionRequest request, ProvisionEntity provisionEntity) {
        CreateAddressRequest addressRequest = request.getAddress();
        AddressEntity addressEntity = AddressEntity.builder()
                .street(addressRequest.getStreet())
                .city(addressRequest.getCity())
                .country(addressRequest.getCountry())
                .postalCode(addressRequest.getPostalCode())
                .build();

        provisionEntity.setId(request.getId());
        provisionEntity.setName(request.getName());
        provisionEntity.setDescription(request.getDescription());
        provisionEntity.setAddress(addressEntity);

        provisionRepo.save(provisionEntity);
    }
}
