package org.example.servicelinkbe.business.provision_service.implementations;

import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.provision_service.interfaces.DeleteProvisionUseCase;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteProvisionUseCaseImpl implements DeleteProvisionUseCase {
    @Override
    public void delete(Long id) {

    }
}
