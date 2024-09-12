package org.example.servicelinkbe.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.servicelinkbe.utilities.DurationToLongConverter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class OfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "name")
    private String name;

    @Convert(converter = DurationToLongConverter.class)
    @Column(name = "duration")
    private Duration duration;

    @NotBlank
    @Length(min = 2, max = 255)
    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "image_path")
    private String imagePath;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id")
    private ServiceProviderEntity serviceProvider;
}
