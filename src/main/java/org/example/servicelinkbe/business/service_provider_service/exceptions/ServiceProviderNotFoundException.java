package org.example.servicelinkbe.business.service_provider_service.exceptions;

public class ServiceProviderNotFoundException extends RuntimeException {
    public ServiceProviderNotFoundException(Long id) {
        super("Service provider with ID " + id + " not found");
    }
}
