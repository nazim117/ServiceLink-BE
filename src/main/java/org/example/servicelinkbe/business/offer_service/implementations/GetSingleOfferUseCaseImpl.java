package org.example.servicelinkbe.business.offer_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.offer_service.interfaces.GetSingleOfferUseCase;
import org.example.servicelinkbe.business.offer_service.utilities.OfferConverter;
import org.example.servicelinkbe.domain.Offer;
import org.example.servicelinkbe.persistance.repositories.OfferRepo;
import org.springframework.stereotype.Service;
import org.example.servicelinkbe.business.offer_service.exception.OfferNotFoundException;

@Service
@AllArgsConstructor
public class GetSingleOfferUseCaseImpl implements GetSingleOfferUseCase {
    private final OfferRepo offerRepo;

    @Transactional
    @Override
    public Offer get(Long offerId) {
        return offerRepo.findById(offerId)
                .map(OfferConverter::convert)
                .orElseThrow(() -> new OfferNotFoundException(offerId));
    }
}
