package org.example.servicelinkbe.domain.create;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.utilities.DurationToLongConverter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOfferRequest {
    @NotBlank
    private String name;
    @NotNull
    private Duration duration;
    @NotBlank
    private String description;
    @NotBlank
    private String imagePath;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Long serviceProviderId;
}
