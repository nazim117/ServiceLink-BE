package org.example.servicelinkbe.business.service_provider_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.service_provider_service.interfaces.GetSingleServiceProviderUseCase;
import org.example.servicelinkbe.business.service_provider_service.utilities.ProvisionConverter;
import org.example.servicelinkbe.domain.ServiceProvider;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetSingleServiceProviderUseCaseImpl implements GetSingleServiceProviderUseCase {
    private final ProvisionRepo provisionRepo;
    @Transactional
    @Override
    public ServiceProvider get(Long id) {
        ServiceProviderEntity serviceProviderEntity = provisionRepo.findById(id).orElse(null);
        if (serviceProviderEntity == null) {
            throw new EntityNotFoundException("Service not found");
        }

        return ProvisionConverter.convert(serviceProviderEntity);
    }
}
