package org.example.servicelinkbe.business.service_provider_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.service_provider_service.interfaces.DeleteServiceProviderUseCase;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteServiceProviderUseCaseImpl implements DeleteServiceProviderUseCase {
    private final ProvisionRepo provisionRepo;

    @Transactional
    @Override
    public void delete(Long id) {
        ServiceProviderEntity serviceProviderEntity = provisionRepo.findById(id).orElse(null);
        if(serviceProviderEntity != null) {
            provisionRepo.delete(serviceProviderEntity);
        }else {
            throw new EntityNotFoundException("Service not found");
        }
    }
}
