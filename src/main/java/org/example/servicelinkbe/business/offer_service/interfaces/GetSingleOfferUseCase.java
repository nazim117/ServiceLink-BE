package org.example.servicelinkbe.business.offer_service.interfaces;

import org.example.servicelinkbe.domain.Offer;

public interface GetSingleOfferUseCase {
    Offer get(Long offerId);
}
