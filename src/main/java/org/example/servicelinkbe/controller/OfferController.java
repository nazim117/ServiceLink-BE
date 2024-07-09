package org.example.servicelinkbe.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.servicelinkbe.business.offer_service.interfaces.CreateOfferUseCase;
import org.example.servicelinkbe.business.offer_service.interfaces.GetOffersUseCase;
import org.example.servicelinkbe.domain.create.CreateOfferRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.domain.get.GetAllOffersResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {
    private final GetOffersUseCase getOffersUseCase;
    private final CreateOfferUseCase createOfferUseCase;

    @GetMapping("{id}")
    public ResponseEntity<GetAllOffersResponse> getOffers(@PathVariable(value = "id") final Long id){
        return ResponseEntity.ok(getOffersUseCase.get(id));
    }

    @PostMapping
    public ResponseEntity<CreateResponse> create(@Valid @RequestBody CreateOfferRequest createOfferRequest){
        try{
            CreateResponse createResponse = createOfferUseCase.create(createOfferRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
        }catch (EntityExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
