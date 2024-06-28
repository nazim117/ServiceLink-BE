package org.example.servicelinkbe.business.provision_service.interfaces;

import org.example.servicelinkbe.domain.create.CreateProvisionRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;

public interface CreateProvisionUseCase {
    CreateResponse create(CreateProvisionRequest request);
}
