package org.example.servicelinkbe.business.provision_service.utilities;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import lombok.NoArgsConstructor;
import org.example.servicelinkbe.domain.Address;
import org.example.servicelinkbe.persistance.entity.AddressEntity;

@NoArgsConstructor
public class AddressConverter {
    public static Address convert(AddressEntity addressEntity) {
        return Address.builder()
                .city(addressEntity.getCity())
                .country(addressEntity.getCountry())
                .postalCode(addressEntity.getPostalCode())
                .street(addressEntity.getStreet())
                .build();
    }

    public static AddressEntity convertToEntity(Address address) {
        return AddressEntity.builder()
                .city(address.getCity())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .street(address.getStreet())
                .build();
    }
}
