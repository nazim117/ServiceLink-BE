package org.example.servicelinkbe.business.offer_service.utilities;

import lombok.NoArgsConstructor;
import org.example.servicelinkbe.domain.Offer;
import org.example.servicelinkbe.persistance.entity.OfferEntity;

@NoArgsConstructor
public class OfferConverter {
    public static Offer convert(OfferEntity offerEntity) {
        return Offer.builder()
                .id(offerEntity.getId())
                .name(offerEntity.getName())
                .description(offerEntity.getDescription())
                .price(offerEntity.getPrice())
                .imageFile(offerEntity.getImagePath())
                .duration(offerEntity.getDuration())
                .build();
    }
}
