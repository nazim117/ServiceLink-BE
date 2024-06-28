package org.example.servicelinkbe.business.provision_service.interfaces;

import org.example.servicelinkbe.domain.Provision;

public interface GetSingleProvisionUseCase {
    Provision get(Long id);
}
