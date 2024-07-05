package org.example.servicelinkbe.business.offer_service;

import lombok.AllArgsConstructor;
import org.example.servicelinkbe.domain.get.GetAllOffersResponse;
import org.example.servicelinkbe.persistance.repositories.OfferRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetOffersUseCaseImpl implements GetOffersUseCase{
    private final OfferRepo offerRepo;

    @Override
    public GetAllOffersResponse get() {
        return null;
    }
}
