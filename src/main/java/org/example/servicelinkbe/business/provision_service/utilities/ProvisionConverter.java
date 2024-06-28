package org.example.servicelinkbe.business.provision_service.utilities;

import lombok.NoArgsConstructor;
import org.example.servicelinkbe.domain.Provision;
import org.example.servicelinkbe.persistance.entity.ProvisionEntity;

@NoArgsConstructor
public class ProvisionConverter {
    public static Provision convert(ProvisionEntity provision){
        return Provision.builder()
                .id(provision.getId())
                .name(provision.getName())
                .address(AddressConverter.convert(provision.getAddress()))
                .description(provision.getDescription())
                .build();
    }
}
