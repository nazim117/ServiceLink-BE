package org.example.servicelinkbe.business.provision_service.implementations;

import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.provision_service.interfaces.CreateProvisionUseCase;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProvisionUseCaseImpl implements CreateProvisionUseCase {
    private final ProvisionRepo provisionRepo;


}
