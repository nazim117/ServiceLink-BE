package org.example.servicelinkbe.business.service_provider_service.utilities;

import lombok.NoArgsConstructor;
import org.example.servicelinkbe.domain.ServiceProvider;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;

@NoArgsConstructor
public class ProvisionConverter {
    public static ServiceProvider convert(ServiceProviderEntity provision){
        return ServiceProvider.builder()
                .id(provision.getId())
                .name(provision.getName())
                .address(AddressConverter.convert(provision.getAddress()))
                .description(provision.getDescription())
                .build();
    }
}
