package org.example.servicelinkbe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    private Long id;
    private String name;
    private Duration duration;
    private String description;
    private String imagePath;
    private BigDecimal price;
}
