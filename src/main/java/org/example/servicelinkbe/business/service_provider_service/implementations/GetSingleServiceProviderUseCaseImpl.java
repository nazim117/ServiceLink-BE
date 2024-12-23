package org.example.servicelinkbe.business.service_provider_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.service_provider_service.exceptions.ServiceProviderNotFoundException;
import org.example.servicelinkbe.business.service_provider_service.interfaces.GetSingleServiceProviderUseCase;
import org.example.servicelinkbe.business.service_provider_service.utilities.ProvisionConverter;
import org.example.servicelinkbe.domain.ServiceProvider;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetSingleServiceProviderUseCaseImpl implements GetSingleServiceProviderUseCase {
    private final ProvisionRepo provisionRepo;
    @Transactional
    @Override
    public ServiceProvider get(Long id) {
        return provisionRepo.findById(id)
                .map(ProvisionConverter::convert)
                .orElseThrow(() -> new ServiceProviderNotFoundException(id));
    }

    @Transactional
    @Override
    public ServiceProvider getByUserId(Long userId) {
        return provisionRepo.findByUserId(userId)
                .map(ProvisionConverter::convert)
                .orElseThrow(() -> new ServiceProviderNotFoundException(userId));
    }
}
