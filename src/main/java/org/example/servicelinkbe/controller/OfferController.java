package org.example.servicelinkbe.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.servicelinkbe.business.offer_service.interfaces.CreateOfferUseCase;
import org.example.servicelinkbe.business.offer_service.interfaces.GetOffersUseCase;
import org.example.servicelinkbe.business.offer_service.interfaces.GetSingleOfferUseCase;
import org.example.servicelinkbe.domain.Offer;
import org.example.servicelinkbe.domain.ServiceProvider;
import org.example.servicelinkbe.domain.create.CreateOfferRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.domain.get.GetAllOffersResponse;
import org.example.servicelinkbe.utilities.ErrorResponse;
import org.example.servicelinkbe.utilities.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
@Slf4j
public class OfferController {
    private final GetOffersUseCase getOffersUseCase;
    private final GetSingleOfferUseCase getSingleOfferUseCase;
    private final CreateOfferUseCase createOfferUseCase;
    private final FileStorageService fileStorageService;

    @GetMapping("{id}")
    public ResponseEntity<GetAllOffersResponse> getOffers(@PathVariable(value = "id") final Long id){
        return ResponseEntity.ok(getOffersUseCase.get(id));
    }

    @GetMapping("/offer/{offerId}")
    public ResponseEntity<Offer> getOfferById(@PathVariable(value = "offerId") final Long offerId){
        Offer offer = null;
        try {
            offer = getSingleOfferUseCase.get(offerId);
            if(offer == null){
                return  ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().body(offer);
    }

    @RequestMapping
    public ResponseEntity<Object> create(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("duration") String durationStr,
            @RequestParam("price") String priceStr,
            @RequestParam("serviceId") String serviceIdStr,
            @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            log.info("Received create offer request with parameters: name={}, description={}, durationStr={}, priceStr={}, serviceIdStr={}",
                    name, description, durationStr, priceStr, serviceIdStr);

            // Validate and parse inputs
            Duration duration = Duration.parse(durationStr);
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceStr));
            Long serviceId = Long.parseLong(serviceIdStr);

            // Log image file details
            log.info("Processing image file: name={}, size={}", imageFile.getOriginalFilename(), imageFile.getSize());

            if (imageFile.isEmpty()) {
                String errorMessage = "No image file provided";
                log.warn(errorMessage);
                return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage));
            }

            String imagePath = fileStorageService.saveImage(imageFile);

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

            log.info("Offer created successfully: {}", createResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);

        } catch (NumberFormatException e) {
            String errorMessage = "Invalid number format in input parameters: " + e.getMessage();
            log.error(errorMessage, e);
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage));
        } catch (DateTimeParseException e) {
            String errorMessage = "Invalid duration format: " + e.getMessage();
            log.error(errorMessage, e);
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage));
        } catch (EntityExistsException e) {
            String errorMessage = "Entity already exists: " + e.getMessage();
            log.warn(errorMessage, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage));
        } catch (AmazonServiceException e) {
            String errorMessage = "Amazon service exception occurred: " + e.getMessage();
            log.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage));
        } catch (SdkClientException e) {
            String errorMessage = "SDK client exception occurred: " + e.getMessage();
            log.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage));
        } catch (Exception e) {
            String errorMessage = "Unexpected error occurred while creating offer: " + e.getMessage();
            log.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage));
        }
    }

}
