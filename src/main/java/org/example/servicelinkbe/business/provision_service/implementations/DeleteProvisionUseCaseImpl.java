package org.example.servicelinkbe.business.provision_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.provision_service.interfaces.DeleteProvisionUseCase;
import org.example.servicelinkbe.persistance.entity.ProvisionEntity;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteProvisionUseCaseImpl implements DeleteProvisionUseCase {
    private final ProvisionRepo provisionRepo;

    @Transactional
    @Override
    public void delete(Long id) {
        ProvisionEntity provisionEntity = provisionRepo.findById(id).orElse(null);
        if(provisionEntity != null) {
            provisionRepo.delete(provisionEntity);
        }else {
            throw new EntityNotFoundException("Service not found");
        }
    }
}
