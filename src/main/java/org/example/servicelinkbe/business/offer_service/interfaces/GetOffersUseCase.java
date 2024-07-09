package org.example.servicelinkbe.business.offer_service.interfaces;

import org.example.servicelinkbe.domain.get.GetAllOffersResponse;

public interface GetOffersUseCase {
    GetAllOffersResponse get(Long id);
}
