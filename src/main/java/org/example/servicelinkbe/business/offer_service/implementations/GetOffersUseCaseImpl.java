package org.example.servicelinkbe.business.offer_service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.offer_service.interfaces.GetOffersUseCase;
import org.example.servicelinkbe.business.offer_service.utilities.OfferConverter;
import org.example.servicelinkbe.domain.Offer;
import org.example.servicelinkbe.domain.get.GetAllOffersResponse;
import org.example.servicelinkbe.persistance.repositories.OfferRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetOffersUseCaseImpl implements GetOffersUseCase {
    private final OfferRepo offerRepo;

    @Transactional
    @Override
    public GetAllOffersResponse get(Long id) {
        List<Offer> offers = offerRepo.findAllById(id)
                .stream()
                .map(OfferConverter::convert)
                .toList();

        if (offers.isEmpty()) {
            throw new EntityNotFoundException("No offers found");
        }

        return GetAllOffersResponse
                .builder()
                .offers(offers)
                .build();
    }
}