package org.example.servicelinkbe.business.provision_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.provision_service.interfaces.GetProvisionsUseCase;
import org.example.servicelinkbe.business.provision_service.utilities.ProvisionConverter;
import org.example.servicelinkbe.domain.Provision;
import org.example.servicelinkbe.domain.get.GetAllProvisionsResponse;
import org.example.servicelinkbe.domain.get.GetAllUsersResponse;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetProvisionsUseCaseImpl implements GetProvisionsUseCase {
    private final ProvisionRepo provisionRepo;
    @Transactional
    @Override
    public GetAllProvisionsResponse get() {
        List<Provision> provisions = provisionRepo.findAll()
                .stream()
                .map(ProvisionConverter::convert)
                .toList();

        return GetAllProvisionsResponse
                .builder()
                .provisions(provisions)
                .build();
    }
}
