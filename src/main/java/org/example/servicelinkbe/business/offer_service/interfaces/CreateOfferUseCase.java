package org.example.servicelinkbe.business.offer_service.interfaces;

import org.example.servicelinkbe.domain.create.CreateOfferRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;

public interface CreateOfferUseCase {
    CreateResponse create(CreateOfferRequest createOfferRequest);
}
