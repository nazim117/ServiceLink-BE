package org.example.servicelinkbe.business.offer_service.implementations;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.offer_service.interfaces.CreateOfferUseCase;
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
public class CreateOfferUseCaseImpl implements CreateOfferUseCase {
    private final OfferRepo offerRepo;
    private final ProvisionRepo provisionRepo;

    @Transactional
    @Override
    public CreateResponse create(CreateOfferRequest request) {
        if(offerRepo.existsByName(request.getName())){
            throw new EntityExistsException("Offer already exists with this name");
        }

        ServiceProviderEntity serviceProvider = provisionRepo.findById(request.getServiceId()).orElse(null);

        if(serviceProvider == null){
            throw new EntityNotFoundException("Service provider not found");
        }
        OfferEntity offerEntity = OfferEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .duration(request.getDuration())
                .imagePath(request.getImagePath())
                .serviceProvider(serviceProvider)
                .build();

        Long provisionId= offerRepo.save(offerEntity).getId();
        return CreateResponse.builder().id(provisionId).build();
    }
}
