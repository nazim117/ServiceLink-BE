package org.example.servicelinkbe.business.service_provider_service.interfaces;

import org.example.servicelinkbe.domain.create.CreateProvisionRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;

public interface CreateServiceProviderUseCase {
    CreateResponse create(CreateProvisionRequest request);
}
