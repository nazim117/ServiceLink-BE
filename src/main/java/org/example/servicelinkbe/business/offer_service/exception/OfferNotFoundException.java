package org.example.servicelinkbe.business.offer_service.exception;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(Long offerId) {
        super("with ID " + offerId + " not found\"");
    }
}
