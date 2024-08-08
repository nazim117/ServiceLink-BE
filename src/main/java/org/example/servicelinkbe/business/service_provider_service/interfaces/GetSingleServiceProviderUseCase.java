package org.example.servicelinkbe.business.service_provider_service.interfaces;

import org.example.servicelinkbe.domain.ServiceProvider;

public interface GetSingleServiceProviderUseCase {
    ServiceProvider get(Long id);
    ServiceProvider getByUserId(Long userId);
}
