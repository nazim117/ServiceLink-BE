package org.example.servicelinkbe.business.service_provider_service.utilities;

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
