package org.example.servicelinkbe.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "addresses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 2, max = 100)
    @Column(name = "street")
    private String street;

    @NotBlank
    @Length(min = 2, max = 100)
    @Column(name = "city")
    private String city;

    @NotBlank
    @Length(min = 2, max = 10)
    @Column(name = "postalCode")
    private String postalCode;

    @NotBlank
    @Length(min = 2, max = 60)
    @Column(name = "country")
    private String country;
}
