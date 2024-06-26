package org.example.servicelinkbe.controller;

import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.provision_service.interfaces.CreateProvisionUseCase;
import org.example.servicelinkbe.business.provision_service.interfaces.GetProvisionsUseCase;
import org.example.servicelinkbe.business.provision_service.interfaces.GetSingleProvisionUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/services")
@AllArgsConstructor
public class ProvisionController {
    private final GetProvisionsUseCase getProvisionsUseCase;
    private final GetSingleProvisionUseCase getSingleProvisionUseCase;
    private final CreateProvisionUseCase createProvisionUseCase;
}
