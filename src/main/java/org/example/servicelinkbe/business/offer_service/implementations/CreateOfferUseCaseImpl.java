package org.example.servicelinkbe.business.offer_service.implementations;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.servicelinkbe.business.offer_service.interfaces.CreateOfferUseCase;
import org.example.servicelinkbe.business.service_provider_service.exceptions.ServiceProviderNotFoundException;
import org.example.servicelinkbe.domain.ServiceProvider;
import org.example.servicelinkbe.domain.create.CreateOfferRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.persistance.entity.OfferEntity;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.persistance.repositories.OfferRepo;
import org.example.servicelinkbe.persistance.repositories.ProvisionRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CreateOfferUseCaseImpl implements CreateOfferUseCase {
    private final OfferRepo offerRepo;
    private final ProvisionRepo provisionRepo;

    @Transactional
    @Override
    public CreateResponse create(CreateOfferRequest request) {
        log.info("Received request to create offer with name: {}", request.getName());

        if (offerRepo.existsByName(request.getName())) {
            log.warn("Offer with name {} already exists", request.getName());
            throw new EntityExistsException("Offer already exists with this name");
        }

        log.debug("Fetching service provider with ID: {}", request.getServiceId());
        ServiceProviderEntity serviceProvider = provisionRepo.findById(request.getServiceId()).orElse(null);

        if (serviceProvider == null) {
            log.error("Service provider not found with ID: {}", request.getServiceId());
            throw new ServiceProviderNotFoundException(request.getServiceId());
        }

        OfferEntity offerEntity = OfferEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .duration(request.getDuration())
                .imagePath(request.getImagePath())
                .serviceProvider(serviceProvider)
                .build();

        log.debug("Saving offer entity: {}", offerEntity);
        Long provisionId = offerRepo.save(offerEntity).getId();
        log.info("Offer created successfully with ID: {}", provisionId);

        return CreateResponse.builder().id(provisionId).build();
    }
}
