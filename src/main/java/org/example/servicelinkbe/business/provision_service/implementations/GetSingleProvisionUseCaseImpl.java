package org.example.servicelinkbe.business.provision_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.provision_service.interfaces.GetSingleProvisionUseCase;
import org.example.servicelinkbe.business.provision_service.utilities.ProvisionConverter;
import org.example.servicelinkbe.domain.Provision;
import org.example.servicelinkbe.persistance.entity.ProvisionEntity;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetSingleProvisionUseCaseImpl implements GetSingleProvisionUseCase {
    private final ProvisionRepo provisionRepo;
    @Transactional
    @Override
    public Provision get(Long id) {
        ProvisionEntity provisionEntity = provisionRepo.findById(id).orElse(null);
        if (provisionEntity == null) {
            throw new EntityNotFoundException("Service not found");
        }

        return ProvisionConverter.convert(provisionEntity);
    }
}
