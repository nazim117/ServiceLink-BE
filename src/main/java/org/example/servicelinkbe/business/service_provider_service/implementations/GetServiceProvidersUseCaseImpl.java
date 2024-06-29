package org.example.servicelinkbe.business.service_provider_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.service_provider_service.interfaces.GetServiceProvidersUseCase;
import org.example.servicelinkbe.business.service_provider_service.utilities.ProvisionConverter;
import org.example.servicelinkbe.domain.ServiceProvider;
import org.example.servicelinkbe.domain.get.GetAllProvisionsResponse;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetServiceProvidersUseCaseImpl implements GetServiceProvidersUseCase {
    private final ProvisionRepo provisionRepo;
    @Transactional
    @Override
    public GetAllProvisionsResponse get() {
        List<ServiceProvider> serviceProviders = provisionRepo.findAll()
                .stream()
                .map(ProvisionConverter::convert)
                .toList();

        return GetAllProvisionsResponse
                .builder()
                .serviceProviders(serviceProviders)
                .build();
    }
}
