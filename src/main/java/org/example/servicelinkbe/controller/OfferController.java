package org.example.servicelinkbe.controller;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.example.servicelinkbe.business.offer_service.interfaces.CreateOfferUseCase;
import org.example.servicelinkbe.business.offer_service.interfaces.GetOffersUseCase;
import org.example.servicelinkbe.domain.create.CreateOfferRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.domain.get.GetAllOffersResponse;
import org.example.servicelinkbe.utilities.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.Duration;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {
    private final GetOffersUseCase getOffersUseCase;
    private final CreateOfferUseCase createOfferUseCase;
    private final FileStorageService fileStorageService;

    @GetMapping("{id}")
    public ResponseEntity<GetAllOffersResponse> getOffers(@PathVariable(value = "id") final Long id){
        return ResponseEntity.ok(getOffersUseCase.get(id));
    }

    @RequestMapping
    public ResponseEntity<CreateResponse> create(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("duration") String durationStr,
            @RequestParam("price") String priceStr,
            @RequestParam("serviceId") String serviceIdStr,
            @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            Duration duration = Duration.parse(durationStr);
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceStr));
            Long serviceId = Long.parseLong(serviceIdStr);
            // Handle image file, e.g., save it and get the path
            String imagePath = fileStorageService.saveImage(imageFile);

            // Create the offer request
            CreateOfferRequest createOfferRequest = CreateOfferRequest.builder()
                    .name(name)
                    .description(description)
                    .duration(duration)
                    .imagePath(imagePath)
                    .price(price)
                    .serviceId(serviceId)
                    .build();

            // Pass the request to the use case
            CreateResponse createResponse = createOfferUseCase.create(createOfferRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
