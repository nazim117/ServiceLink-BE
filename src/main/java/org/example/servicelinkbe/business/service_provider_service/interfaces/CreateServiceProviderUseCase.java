package org.example.servicelinkbe.business.service_provider_service.interfaces;

import org.example.servicelinkbe.domain.create.CreateServiceProviderRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;

public interface CreateServiceProviderUseCase {
    CreateResponse create(CreateServiceProviderRequest request);
}
