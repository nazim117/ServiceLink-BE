package org.example.servicelinkbe.controller;

import lombok.RequiredArgsConstructor;
import org.example.servicelinkbe.business.offer_service.GetOffersUseCase;
import org.example.servicelinkbe.domain.get.GetAllOffersResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {
    private final GetOffersUseCase getOffersUseCase;
    @GetMapping
    public ResponseEntity<GetAllOffersResponse> getOffers(){
        return ResponseEntity.ok(getOffersUseCase.get());
    }
}
